package com.example.application.security;

import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityService {

    private final AuthenticationContext authenticationContext;

    public SecurityService(AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
    }

    public UserDetails getAuthenticatedUser() {
        Optional<UserDetails> authenticatedUser = authenticationContext.getAuthenticatedUser(UserDetails.class);
        if (authenticatedUser.isPresent()) {
            return authenticatedUser.get();
        }
        return null;
    }

    public Boolean isLoggedIn() {
        return this.getAuthenticatedUser() != null;
    }

    public String getLoginUserUsername() {
        UserDetails authenticatedUser = this.getAuthenticatedUser();
        if (authenticatedUser != null) {
            return authenticatedUser.getUsername();
        }
        return null;
    }

    public void logout() {
        authenticationContext.logout();
    }
}