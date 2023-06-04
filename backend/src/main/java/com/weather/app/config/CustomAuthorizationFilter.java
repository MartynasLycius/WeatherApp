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
import lombok.NonNull;
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

/**
 * authorization filter class
 *
 * @author raihan
 */
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserDetailsService userService;
    private final LogUtils logUtils;
    static ErrorMessage ERROR_MESSAGE;

    /**
     * setting ErrorMessage
     *
     * @param date    type Date
     * @param message type String
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
     *
     * @param request     type HttpServletRequest
     * @param response    type HttpServletResponse
     * @param filterChain type FilterChain
     * @author raihan
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws IOException, ServletException {
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
                authentication = getAuthentication(request, response);
            } catch (ExpiredJwtException ex) {
                setErrorMessge(new Date(), ErrorMessages.TOKEN_IS_EXPIRED);
                response.setContentType(Constants.APPLICATION_JSON);
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                new ObjectMapper().writeValue(response.getOutputStream(), ERROR_MESSAGE);
                logUtils.printErrorLog(ex.getLocalizedMessage(), request.getRequestURI());
                return;
            } catch (TokenInvalidException ex) {
                setErrorMessge(new Date(), ErrorMessages.TOKEN_IS_NOT_VALID);
                response.setContentType(Constants.APPLICATION_JSON);
                new ObjectMapper().writeValue(response.getOutputStream(), ERROR_MESSAGE);
                logUtils.printErrorLog(ex.getLocalizedMessage(), request.getRequestURI());
                return;
            }
            if (authentication != null) SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }

    }

    /**
     * setting UsernamePasswordAuthenticationToken
     *
     * @param request type HttpServletRequest
     * @return UsernamePasswordAuthenticationToken
     * @author raihan
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader(Constants.AUTHORIZATION_HEADER);
        if (token != null) {
            token = token.substring(Constants.BEARER.length());
            String user = jwtUtils.extractUsername(token);
            if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(user);
                if (!jwtUtils.isTokenValid(token, userDetails)) {
                    throw new TokenInvalidException(ErrorMessages.TOKEN_IS_NOT_VALID);
                }
                logUtils.printLog("Token is valid", request.getRequestURI());
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}