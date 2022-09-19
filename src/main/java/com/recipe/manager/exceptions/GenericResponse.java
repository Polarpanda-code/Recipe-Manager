package com.recipe.manager.exceptions;

/**
 * This class represents a generic response by recipe manager
 */
public class GenericResponse {

  private final String message;

  public GenericResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}

