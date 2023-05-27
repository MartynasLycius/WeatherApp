package com.weather.app.controller;

import com.weather.app.model.UserRequestModel;
import com.weather.app.model.UserResponseModel;
import com.weather.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * this is user api controller
 *
 * @author raihan
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * api for user registration
     *
     * @param userRequestModel
     * @return
     * @throws Exception
     * @author raihan
     */
    @PostMapping("/register")
    public UserResponseModel registerUser(@RequestBody UserRequestModel userRequestModel) throws Exception {
        return userService.registerUser(userRequestModel);
    }

    /**
     * api for getting user info by id
     *
     * @param id
     * @return UserResponseModel
     * @author raihan
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseModel> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

}