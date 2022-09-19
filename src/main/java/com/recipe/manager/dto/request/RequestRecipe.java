package com.recipe.manager.dto.request;


import com.recipe.manager.enumvalidator.ValueOfEnum;
import com.recipe.manager.model.Ingredient;
import com.recipe.manager.model.RecipeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


/**
 * main dto to request from controller
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestRecipe {

  @NotBlank(message = "{recipeName.notBlank}")
  @Size(max = 10)
  @ApiModelProperty(notes = "The name of the recipe", example = "Pasta")
  private String name;

  @ApiModelProperty(notes = "The type of the recipe", example = "VEGETARIAN")
  @ValueOfEnum(enumClass = RecipeType.class)
  private String type;

  @NotNull(message = "{numberOfServings.notNull}")
  @Positive(message = "{numberOfServings.positive}")
  @ApiModelProperty(notes = "The number of servings per recipe", example = "4")
  private int numberOfServings;

  @ApiModelProperty(notes = "the ingredients needed to make the recipe", example = "Paneer")
  private List<Ingredient> ingredientsName;

  @NotBlank(message = "{instructions.notBlank}")
  @Size(max = 10)
  @ApiModelProperty(notes = "The instructions to create the recipe", example = "fry, heat ,bake, boil the eggs")
  private String instructions;


}
