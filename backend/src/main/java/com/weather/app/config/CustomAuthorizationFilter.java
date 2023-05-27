package com.weather.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.app.constants.Constants;
import com.weather.app.exceptions.ErrorMessage;
import com.weather.app.exceptions.TokenInvalidException;
import com.weather.app.utils.JwtUtils;
import com.weather.app.utils.LogUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserDetailsService userService;
    private final LogUtils logUtils;
    static ErrorMessage ERROR_MESSAGE;

    /**
     * setting ErrorMessage
     *
     * @param date
     * @param message
     * @author raihan
     */
    public void setErrorMessge(Date date, String message) {
        ERROR_MESSAGE = ErrorMessage.builder()
                .timeStamp(date)
                .message(message)
                .build();
    }

    /**
     * authorization method
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token;
        if (request.getServletPath().equals(Constants.LOGIN_ENDPOINT)) {
            filterChain.doFilter(request, response);
            return;
        } else {
            String authHeader = request.getHeader(Constants.AUTHORIZATION_HEADER);
            if (authHeader == null || !authHeader.startsWith(Constants.BEARER)) {
                filterChain.doFilter(request, response);
                return;
            }
            UsernamePasswordAuthenticationToken authentication = null;
            try {
                authentication = getAuthentication(request,response);
            }catch (ExpiredJwtException ex){
                setErrorMessge(new Date(),ErrorMessages.TOKEN_IS_EXPIRED.getErrorMessage());
                response.setContentType(Constants.APPLICATION_JSON);
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                new ObjectMapper().writeValue(response.getOutputStream(), ERROR_MESSAGE);
                logUtils.printErrorLog(ex.getLocalizedMessage(),request.getRequestURI());
                return;
            }catch (TokenInvalidException ex){
                setErrorMessge(new Date(),ErrorMessages.TOKEN_IS_NOT_VALID.getErrorMessage());
                response.setContentType(Constants.APPLICATION_JSON);
                new ObjectMapper().writeValue(response.getOutputStream(), ERROR_MESSAGE);
                logUtils.printErrorLog(ex.getLocalizedMessage(),request.getRequestURI());
                return;
            }
            if (authentication != null) SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }

    }

    /**
     * setting UsernamePasswordAuthenticationToken
     * @param request
     * @return UsernamePasswordAuthenticationToken
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String token = request.getHeader(Constants.AUTHORIZATION_HEADER);
        if (token != null) {
            token = token.substring(Constants.BEARER.length());
            String user = null;
            user = jwtUtils.extractUsername(token);
            if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(user);
                if (!jwtUtils.isTokenValid(token,userDetails)) {
                    throw new TokenInvalidException(ErrorMessages.TOKEN_IS_NOT_VALID.getErrorMessage());
                }
                logUtils.printLog("Token is valid", request.getRequestURI());
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}