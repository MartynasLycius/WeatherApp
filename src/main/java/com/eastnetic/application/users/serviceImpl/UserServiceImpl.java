package com.eastnetic.application.users.serviceImpl;

import com.eastnetic.application.users.dto.UserDto;
import com.eastnetic.application.users.entity.User;
import com.eastnetic.application.users.exceptions.UserException;
import com.eastnetic.application.users.repository.UserRepository;
import com.eastnetic.application.users.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserDto userDto) throws Exception {

        User user = userRepository.findByUsername(userDto.getUsername());

        if (user != null) {
            throw new UserException("This username already registered.");
        }

         user = new User(
                userDto.getUsername(),
                passwordEncoder.encode(userDto.getPassword())
        );

        try {

            userRepository.save(user);

            LOGGER.info("Registration success.");

        } catch (Exception e) {

            LOGGER.error("Registration error : ", e);

            throw e;
        }
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
