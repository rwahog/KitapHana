package com.kitaphana.exceptions;

public class DocumentNotFoundException extends RuntimeException {

  private static final String message = "Document was not found.";

  @Override
  public String getMessage() {
    return message;
  }
}
