package com.weather.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * App configuration class
 *
 * @author raihan
 */
@Configuration
public class AppConfig {
    /**
     * bean of passwordEncoder returns instance of BcryptPasswordEncoder
     *
     * @return PasswordEncoder
     * @author raihan
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
