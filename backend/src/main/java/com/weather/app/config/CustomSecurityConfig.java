package com.weather.app.config;

import com.weather.app.constants.Endpoints;
import com.weather.app.utils.JwtUtils;
import com.weather.app.utils.LogUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * custom security configuration
 *
 * @author raihan
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CustomSecurityConfig {
    private final JwtUtils jwtUtils;
    private final UserDetailsService userService;
    private final LogUtils logUtils;

    /**
     * method for configure security
     *
     * @param http                  type HttpSecurity
     * @param authenticationManager type AuthenticationManager
     * @return SecurityFilterChain
     * @author raihan
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth ->
                                auth
                                        .requestMatchers("/hello/**", Endpoints.LOGIN, Endpoints.REGISTRATION,
                                                Endpoints.API_LOCATION + Endpoints.ENDPOINT_PATTERN,
                                                Endpoints.API_WEATHER + Endpoints.ENDPOINT_PATTERN)
                                        .permitAll()
                                        .anyRequest().authenticated()
                )
                .addFilter(new CustomAuthenticationFilter(authenticationManager, jwtUtils, userService, logUtils))
                .addFilterBefore(new CustomAuthorizationFilter(jwtUtils, userService, logUtils), UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }

    /**
     * bean for authenticationManager
     *
     * @param config type AuthenticationConfiguration
     * @return AuthenticationManager
     * @author raihan
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
