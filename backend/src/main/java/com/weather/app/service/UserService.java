package com.weather.app.service;


import com.weather.app.model.UserRequestModel;
import com.weather.app.model.UserResponseModel;

public interface UserService {
    UserResponseModel registerUser(UserRequestModel userRequestModel) throws Exception;

    UserResponseModel getUserByEmail(String username);

    UserResponseModel getUserById(Long id);
}