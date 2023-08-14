package com.eastnetic.application.locations.exceptions;


public class LocationDataException extends RuntimeException {

    public LocationDataException(String message) {
        super(message);
    }

    public LocationDataException(String message, Throwable cause) {
        super(message, cause);
    }
}