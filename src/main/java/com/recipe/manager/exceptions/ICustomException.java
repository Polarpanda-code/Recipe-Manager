package com.recipe.manager.exceptions;

import org.springframework.http.HttpStatus;

  public interface ICustomException {
    HttpStatus getStatus();
  }