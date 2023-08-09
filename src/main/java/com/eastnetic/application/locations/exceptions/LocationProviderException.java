package com.eastnetic.application.locations.exceptions;


public class LocationProviderException extends RuntimeException {

    public LocationProviderException(String message) {
        super(message);
    }

    public LocationProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
