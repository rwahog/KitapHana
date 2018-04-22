package com.kitaphana.exceptions;

public class KeywordNotFoundException extends RuntimeException {

    private static final String message = "Keyword was not found.";

    @Override
    public String getMessage() {
        return message;
    }
}
