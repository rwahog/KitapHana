package com.kitaphana.exceptions;

public class AuthorNotFoundException extends RuntimeException {

  private static final String message = "Author was not found.";

  @Override
  public String getMessage() {
    return message;
  }
}
