package com.weather.app.exceptions;

import java.io.Serial;

public class UserServiceException extends RuntimeException{
    public UserServiceException(String message){
        super(message);
    }
}