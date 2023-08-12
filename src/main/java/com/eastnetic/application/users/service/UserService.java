package com.eastnetic.application.users.service;

import com.eastnetic.application.users.dto.UserDto;
import com.eastnetic.application.users.entity.User;

public interface UserService {

    void registerUser(UserDto userDto) throws Exception;

    User findUserByUsername(String email);
}
