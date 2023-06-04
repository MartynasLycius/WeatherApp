package com.weather.app.exceptions;

public class TokenInvalidException extends RuntimeException{
    public TokenInvalidException(String message){
        super(message);
    }
}