package com.weather.app.exceptions;

import java.io.Serial;

public class UserServiceException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    public UserServiceException(String message){
        super(message);
    }
}