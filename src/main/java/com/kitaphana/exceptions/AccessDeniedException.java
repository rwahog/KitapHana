package com.kitaphana.exceptions;

public class AccessDeniedException extends RuntimeException {

  private static final String message = "Access denied";
  private String path;

  public AccessDeniedException(String path) {
    this.path = path;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public String getLocalizedMessage() {
    return path;
  }
}
