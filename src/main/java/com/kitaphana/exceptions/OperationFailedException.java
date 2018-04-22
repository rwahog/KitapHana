package com.kitaphana.exceptions;

public class OperationFailedException extends RuntimeException {

    private static final String message = "Failed to perform operation. Try again.";

    @Override
    public String getMessage() {
        return message;
    }
}
