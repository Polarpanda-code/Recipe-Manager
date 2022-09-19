package com.recipe.manager.enumvalidator;

import com.fasterxml.jackson.annotation.JsonValue;

  public interface TitleAware<E extends Enum<E>> {

    @JsonValue
    String getTitle();

  }


