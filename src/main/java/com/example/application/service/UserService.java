package com.example.application.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    void addUser(Long id, String username, String password);

    boolean isUserLoggedIn();

    Long getCurrentUserId();

    void logout();

    UserDetails getUserDetails();
}
