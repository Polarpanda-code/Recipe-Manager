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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * Request is used for updating recipe catalogue
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRecipeRequest extends BasicRequest{
  @NotBlank(message = "notBlank")
  @Size(max = 10)
  @Pattern(regexp = "^(?:\\p{L}\\p{M}*|[',. \\-]|\\s)*$")
  @ApiModelProperty(notes = "The name of the ingredient", example = "Potato")
  private String name;

  @ValueOfEnum(enumClass = RecipeType.class, message = "{recipeType.invalid}")
  @ApiModelProperty(notes = "The type of the recipe", example = "VEGETARIAN")
  private String type;

  @NotNull(message = "{numberOfServings.notNull}")
  @Positive(message = "{numberOfServings.positive}")
  @ApiModelProperty(notes = "The number of servings", example = "7")
  private int numberOfServings;

  @ApiModelProperty(notes = "The new ids of the ingredients needed for the update", example = "[3,4]")
  private List<Ingredient> ingredients;

  @NotBlank(message = "{instructions.notBlank}")
  @Size(max = 100)
  @ApiModelProperty(notes = "The instructions to update the recipe", example = "Cut,fry,eat")

  private String instructions;


}
