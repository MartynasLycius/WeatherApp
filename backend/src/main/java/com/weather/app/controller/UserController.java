package com.weather.app.controller;

import com.weather.app.constants.Endpoints;
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
@RequestMapping(Endpoints.API_USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * api for user registration
     *
     * @param userRequestModel request body
     * @return UserResponseModel as responseEntity
     * @author raihan
     */
    @PostMapping(Endpoints.API_USER_REGISTER)
    public ResponseEntity<UserResponseModel> registerUser(@RequestBody UserRequestModel userRequestModel) throws Exception {
        return new ResponseEntity<>(userService.registerUser(userRequestModel), HttpStatus.CREATED);
    }

    /**
     * api for getting user info by id
     *
     * @param id is specific user id
     * @return UserResponseModel
     * @author raihan
     */
    @GetMapping(Endpoints.API_GET_USER_BY_ID)
    public ResponseEntity<UserResponseModel> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

}