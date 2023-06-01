package com.example.application.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.example.application.backend.repository.UserRepository;
import com.example.application.backend.service.UserService;
import com.example.application.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

	@Autowired
	UserService userService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().requestMatchers("/images/*.png").permitAll();
		super.configure(http);
		setLoginView(http, LoginView.class);
	}

	@Bean
	public UserDetailsService users() {
		UserDetails user = User.builder().username(userService.getAllUser().get(0).getName())
				.password(this.passwordEncoder().encode(userService.getAllUser().get(0).getPassword())).roles("USER").build();
		return new InMemoryUserDetailsManager(user);
	}

	@Bean
	@Primary
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}