package org.weather.app.service;

import org.springframework.stereotype.Service;
import org.weather.app.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
