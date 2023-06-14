package com.example.application.service.impl;

import com.example.application.data.entity.User;
import com.example.application.data.repository.UserRepository;
import com.example.application.service.UserService;
import com.vaadin.flow.spring.security.AuthenticationContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AuthenticationContext authenticationContext;

    private final PasswordEncoder passwordEncoder;


    public UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails;
        }
        return null;
    }

    public void addUser(Long id, String username, String password) {
        User user = User.builder()
                .id(id)
                .name(username)
                .enabled(true)
                .password(passwordEncoder.encode(password))
                .username(username)
                .build();

        if (!userRepository.existsById(user.getId()))
            userRepository.save(user);
    }

    public boolean isUserLoggedIn() {
        return getUserDetails() != null;
    }

    public Long getCurrentUserId() {
        log.debug("getCurrentUserId called");

        UserDetails userDetails = getUserDetails();
        if (userDetails == null) {
            return null;
        }
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Invalid User"));
        return user.getId();
    }

    public void logout() {
        authenticationContext.logout();
    }

}
