package com.kitaphana.exceptions;

public class UserNotFoundException extends RuntimeException {

    private static final String message = "User was not found.";

    @Override
    public String getMessage() {
        return message;
    }

}
