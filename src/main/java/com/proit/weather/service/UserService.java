package com.proit.weather.service;

import com.proit.weather.model.User;
import com.proit.weather.repository.UserRepository;
import jakarta.validation.Valid;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public User findUserByUserName(String userName) {
        return userRepository.findUser(userName);
    }

    public void createUser(@Valid User user) {
        userRepository.createUser(user);
    }
}
