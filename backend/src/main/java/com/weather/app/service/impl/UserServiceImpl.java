package com.weather.app.service.impl;

import com.weather.app.config.ErrorMessages;
import com.weather.app.entity.User;
import com.weather.app.model.UserRequestModel;
import com.weather.app.model.UserResponseModel;
import com.weather.app.repository.UserRepository;
import com.weather.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * userService implementation class
 *
 * @author raihan
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * method for registering user
     *
     * @param userRequestModel type UserRequestModel
     * @return UserResponseModel
     * @author raihan
     */
    @Override
    public UserResponseModel registerUser(UserRequestModel userRequestModel) throws Exception {
        if (!userRequestModel.getPassword().equals(userRequestModel.getPassword2()))
            throw new Exception(ErrorMessages.PASSWORD_NOT_MATCHED);
        if (isUserExist(userRequestModel.getEmail())) {
            throw new Exception(ErrorMessages.RECORD_ALREADY_EXISTS);
        }
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userRequestModel, User.class);
        user.setPassword(passwordEncoder.encode(userRequestModel.getPassword()));
        User storedUser = userRepository.save(user);
        return modelMapper.map(storedUser, UserResponseModel.class);
    }

    /**
     * method for get user by email
     *
     * @param username type String
     * @return UserResponseModel
     * @author raihan
     */
    @Override
    public UserResponseModel getUserByEmail(String username) {
        User user = userRepository.findByEmail(username);
        if (user == null) throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND_BY_EMAIL + username);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserResponseModel.class);
    }

    /**
     * method for get user by id
     *
     * @param id type long
     * @return UserResponseModel
     * @author raihan
     */
    @Override
    public UserResponseModel getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND_BY_ID + id);
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user.get(), UserResponseModel.class);
    }

    /**
     * method for checking existing user
     *
     * @return boolean
     * @author raihan
     */
    private boolean isUserExist(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    /**
     * method for load username by email
     *
     * @return UserDetails
     * @author raihan
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND_BY_EMAIL + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}