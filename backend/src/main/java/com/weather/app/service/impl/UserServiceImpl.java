package com.weather.app.service.impl;

import com.weather.app.entity.User;
import com.weather.app.model.UserRequestModel;
import com.weather.app.model.UserResponseModel;
import com.weather.app.repository.UserRepository;
import com.weather.app.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponseModel registerUser(UserRequestModel userRequestModel) throws Exception {
        if (!userRequestModel.getPassword().equals(userRequestModel.getPassword2()))
            throw new Exception("password does not matched");
        if (isUserExist(userRequestModel.getEmail())) {
            throw new Exception("User already exist");
        }
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userRequestModel, User.class);
        user.setPassword(passwordEncoder.encode(userRequestModel.getPassword()));
        User storedUser = userRepository.save(user);
        return modelMapper.map(storedUser, UserResponseModel.class);
    }

    @Override
    public UserResponseModel getUserByEmail(String username) {
        User user = userRepository.findByEmail(username);
        if (user == null) throw new UsernameNotFoundException("User not found by " + username);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserResponseModel.class);
    }

    @Override
    public UserResponseModel getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found by id: " + id);
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
            throw new UsernameNotFoundException("Username not found for " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}