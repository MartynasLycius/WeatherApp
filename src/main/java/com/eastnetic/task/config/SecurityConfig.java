package com.eastnetic.task.config;

import com.eastnetic.task.views.login.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    /**
     * Web security config for spring security
     * @param http
     * @return
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // http.rememberMe().alwaysRemember(false);
        http.csrf().disable();
        http.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/public/**"))
                .permitAll();

        super.configure(http);

        setLoginView(http, LoginView.class);
    }

    /**
     * Bean declaration for password encoder
     * @param
     * @return BCryptPasswordEncoder
     * @throws
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
