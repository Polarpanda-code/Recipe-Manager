package com.recipe.manager.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class RecipeNotFoundException extends RuntimeException{


  private HttpStatus status = HttpStatus.NOT_FOUND;

  public RecipeNotFoundException() {
    super();
  }

  public RecipeNotFoundException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public RecipeNotFoundException(String message) {
    super(message);
  }

  public RecipeNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public RecipeNotFoundException(Throwable cause) {
    super(cause);
  }

  protected RecipeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public HttpStatus getStatus() {
    return status;
  }
}


