package com.weatherapp.services;

import com.weatherapp.model.User;
import org.springframework.stereotype.Service;

/**

 * Created By - Shamiul Islam
 * Created by rasel on 13 Jun, 2024
 */
@Service
public interface UserService {
//
    
//
    public User getUser(String userName);

    /**
     * This method is used to register a new user.
     *
     * @param userName The username of the new user.
     * @param password The password of the new user.
     *
     * The method should create a new User object and set the provided username and password to the User object.
     * Finally, it should save the User object to the userRepository.
     */
    public void register(String userName, String password);
}
