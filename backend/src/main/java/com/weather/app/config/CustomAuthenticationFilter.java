package com.weather.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.app.SpringApplicationContext;
import com.weather.app.constants.Constants;
import com.weather.app.model.UserResponseModel;
import com.weather.app.service.UserService;
import com.weather.app.utils.JwtUtils;
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

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = null;
        String password = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, String> requestMap = objectMapper.readValue(request.getInputStream(), Map.class);
            username = requestMap.get("email");
            password = requestMap.get("password");
            log.info("Login with " + username);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (AuthenticationException e) {
            catchActionForAuthenticationException(response, e);
            //throw new RuntimeException(String.format("Error in attemptAuthentication with username: %s", username));
        } catch (Exception e) {
            catchActionForAuthenticationException(response, e);
           // throw new RuntimeException(String.format("Error in attemptAuthentication with username: %s", username));
        }
        return null;
    }

    private void catchActionForAuthenticationException(HttpServletResponse response,
                                                       Exception e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("errorMessage", e.getMessage());
        response.setContentType("application/json");
        try {
            new ObjectMapper().writeValue(response.getOutputStream(), errorMessage);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String username = ((User) authResult.getPrincipal()).getUsername();
        String token = jwtUtils.generateToken(userDetailsService.loadUserByUsername(username));
        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
        UserResponseModel user = userService.getUserByEmail(username);
        response.setHeader(Constants.AUTHORIZATION_HEADER, Constants.BEARER + token);
        response.setHeader("UserID", user.getId());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("errorMessage", "Bad credentials");
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), errorMessage);

    }
}