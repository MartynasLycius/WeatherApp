package com.eastnetic.application.locations.exceptions;

public class FavouriteLocationException extends RuntimeException {

    public FavouriteLocationException(String message) {
        super(message);
    }

    public FavouriteLocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
