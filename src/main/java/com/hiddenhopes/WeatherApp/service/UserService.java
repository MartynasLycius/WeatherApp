package com.hiddenhopes.WeatherApp.service;

import com.hiddenhopes.WeatherApp.model.User;

public interface UserService {
    User findByUsername(String username);
}
