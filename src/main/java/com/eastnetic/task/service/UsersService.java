package com.eastnetic.task.service;

import com.eastnetic.task.model.dao.Users;

public interface UsersService {

    void addUser(String username, String password, String roles, String name);

    boolean isUserLoggedIn();

    Users getCurrentUser();

    void logout();

    String getUsernameFromPrincipal();
}
