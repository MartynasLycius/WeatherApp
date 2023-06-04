package com.weather.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.app.SpringApplicationContext;
import com.weather.app.constants.Constants;
import com.weather.app.model.LoginResponseModel;
import com.weather.app.model.UserResponseModel;
import com.weather.app.service.UserService;
import com.weather.app.utils.JwtUtils;
import com.weather.app.utils.LogUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * customAuthenticationFilter for authentication process
 *
 * @author raihan
 */
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;
    private final LogUtils logUtils;

    /**
     * method for attemptAuthentication
     *
     * @param request  type HttpServletRequest
     * @param response type HttpServletResponse
     * @return Authentication
     * @author raihan
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = null;
        String password = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, String> requestMap = objectMapper.readValue(request.getInputStream(), Map.class);
            username = requestMap.get("email");
            password = requestMap.get("password");
            logUtils.printLog("Logged in by user: " + username, request.getContextPath());
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (AuthenticationException e) {
            logUtils.printErrorLog("Authentication error: by user " + username, request.getContextPath());
            catchActionForAuthenticationException(response, e);
        } catch (Exception e) {
            logUtils.printErrorLog("Authentication error: by user " + username, request.getContextPath());
            catchActionForAuthenticationException(response, e);
        }
        return null;
    }

    /**
     * method for catching authentication exception
     *
     * @param response type HttpServletResponse
     * @param e        type Exception
     * @author raihan
     */
    private void catchActionForAuthenticationException(HttpServletResponse response,
                                                       Exception e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put(Constants.ERROR_MESSAGE_KEY, e.getMessage());
        response.setContentType(Constants.APPLICATION_JSON);
        try {
            new ObjectMapper().writeValue(response.getOutputStream(), errorMessage);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * method for creating success response
     *
     * @param request    type HttpServletRequest
     * @param response   type HttpServletResponse
     * @param chain      type FilterChain
     * @param authResult type Authentication
     * @author raihan
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String username = ((User) authResult.getPrincipal()).getUsername();
        String token = jwtUtils.generateToken(userDetailsService.loadUserByUsername(username));
        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
        UserResponseModel user = userService.getUserByEmail(username);
        LoginResponseModel loginResponseModel = LoginResponseModel.builder()
                .userId(user.getId())
                .token(Constants.BEARER + token)
                .build();
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(Constants.APPLICATION_JSON);
        new ObjectMapper().writeValue(response.getOutputStream(), loginResponseModel);
    }

    /**
     * method for creating unsuccessful Authentication response
     *
     * @param request  type HttpServletRequest
     * @param response type HttpServletResponse
     * @param failed   type AuthenticationException
     * @author raihan
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put(Constants.ERROR_MESSAGE_KEY, ErrorMessages.BAD_CREDENTIALS);
        response.setContentType(Constants.APPLICATION_JSON);
        new ObjectMapper().writeValue(response.getOutputStream(), errorMessage);

    }
}