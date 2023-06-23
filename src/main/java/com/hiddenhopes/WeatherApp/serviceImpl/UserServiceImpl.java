package com.hiddenhopes.WeatherApp.serviceImpl;

import com.hiddenhopes.WeatherApp.model.User;
import com.hiddenhopes.WeatherApp.repository.UserRepository;
import com.hiddenhopes.WeatherApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
