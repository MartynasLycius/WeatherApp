package com.example.application.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    boolean isUserLoggedIn();

    Long getCurrentUserId();

    void logout();

    UserDetails getUserDetails();
}
