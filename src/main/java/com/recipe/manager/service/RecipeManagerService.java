package com.recipe.manager.service;

import com.recipe.manager.dto.request.RequestRecipe;
import com.recipe.manager.dto.request.UpdateRecipeRequest;
import com.recipe.manager.dto.response.CustomSearchResponse;
import com.recipe.manager.exceptions.MessageProvider;
import com.recipe.manager.exceptions.RecipeNotFoundException;
import com.recipe.manager.model.Ingredient;
import com.recipe.manager.model.Recipe;
import com.recipe.manager.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * service class for recipe manager
 */
@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class RecipeManagerService {
  private final RecipeRepository recipeRepository;
  private final MessageProvider messageProvider;

  //this service check database for the recipe name and returns it else throws exception
  /**
   *
   * @param name
   * @return recipe object
   */
  public Recipe fetchRecipeByName(String name){

    Recipe recipeByName = recipeRepository.findRecipeByName(name);
    if (recipeByName == null)
      throw new RecipeNotFoundException("Recipe : "+ name+ " not found",
          HttpStatus.NOT_FOUND);
    return recipeByName;

  }

  /**
   *
   * @param requestRecipe
   * @return recipe id
   */
  public Integer create(RequestRecipe requestRecipe) {

    Recipe build = Recipe.builder().instructions(requestRecipe.getInstructions())
        .name(requestRecipe.getName())
        .numberOfServings(requestRecipe.getNumberOfServings())
        .type(requestRecipe.getType())
        .recipeIngredients(requestRecipe.getIngredientsName()).build();
    log.info("saving Recipe object"+ build.toString());
    recipeRepository.save(build);

    return build.getId();
  }

  /**
   * deleting the recipe
   * @param id
   */
  public void deleteRecipe(Integer id) {
    if (!recipeRepository.existsById(id)) {
      throw new RecipeNotFoundException(messageProvider.getMessage("recipe.notFound"));
    }
    recipeRepository.deleteById(id);
  }

  /**
   * updating the recipe
   * @param updateRecipeRequest
   */
  public void update(UpdateRecipeRequest updateRecipeRequest) {
    Recipe recipe = recipeRepository.findById(updateRecipeRequest.getId())
        .orElseThrow(() -> new RecipeNotFoundException(messageProvider.getMessage("recipe.notFound")));
    recipe.setRecipeIngredients(updateRecipeRequest.getIngredients());
    recipe.setName(updateRecipeRequest.getName());
    recipe.setType(updateRecipeRequest.getType());
    recipe.setNumberOfServings(updateRecipeRequest.getNumberOfServings());
    recipe.setInstructions(updateRecipeRequest.getInstructions());
    recipeRepository.save(recipe);
    }

  /**
   * getting the recipe list<Recipe>
   * @return List<Recipe>
   */
    public List<Recipe> getRecipeList() {
      return recipeRepository.findAll();
    }

  public List<Recipe> search(String[] search) {
    String recipeType,ingredients, instructions;
        Integer numberOfServings;
    recipeType=search[0];
    numberOfServings=Integer.valueOf(search[1]);
    ingredients=search[2];
    instructions=search[3];
    return Optional.ofNullable(recipeRepository.findByCustomSearch( recipeType, ingredients,  instructions, numberOfServings))
        .orElseThrow(()->new RecipeNotFoundException(String.format("NO Recipe Found")));

  }
}
