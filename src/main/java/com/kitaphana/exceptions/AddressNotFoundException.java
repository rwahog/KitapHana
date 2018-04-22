package com.kitaphana.exceptions;

public class AddressNotFoundException extends RuntimeException {

    private static final String message = "Address was not found.";

    @Override
    public String getMessage() {
        return message;
    }

}
