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
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final AuthenticationContext authenticationContext;


    public UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails;
        }
        return null;
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
        User user = repository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Invalid User"));
        return user.getId();
    }

    public void logout() {
        authenticationContext.logout();
    }

}
