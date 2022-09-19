package com.recipe.manager.dto.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * this is the basic structure for request ot update recipes
 */
public class BasicRequest {

  @NotNull()
  @Positive()
  @ApiModelProperty(notes = "Id of the attribute", example = "1")
  private Integer id;

  public Integer getId() {
    return id;
  }

  public BasicRequest() {
  }

  public BasicRequest(Integer id) {
    this.id = id;
  }
}
