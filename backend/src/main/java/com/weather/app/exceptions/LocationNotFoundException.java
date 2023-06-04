package com.weather.app.exceptions;

public class LocationNotFoundException extends RuntimeException{
    public LocationNotFoundException(String message){
        super(message);
    }
}
