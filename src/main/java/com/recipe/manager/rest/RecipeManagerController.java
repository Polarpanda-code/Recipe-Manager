package com.recipe.manager.rest;


import com.recipe.manager.dto.request.RequestRecipe;
import com.recipe.manager.dto.request.UpdateRecipeRequest;
import com.recipe.manager.dto.response.CustomSearchResponse;
import com.recipe.manager.model.Recipe;
import com.recipe.manager.service.RecipeManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created controller class for entry of the request to Recipe-Manager api
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/recipes/v1")
@Slf4j
@Api(value = "RecipeController", tags = "Recipe Controller", description = "Create, update, delete, list recipes")
public class RecipeManagerController {

  private final RecipeManagerService recipeManagerService;

  //endpoint to fetch recipe by name. For simplicity it is returning a single recipe.
  /**
   *
   * @param recipeName
   * @return recipe object with given name
   */
  @ApiOperation(value = "List one recipe by its ID", response = Recipe.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successful request"),
      @ApiResponse(code = 404, message = "Recipe not found by the given ID")
  })
  @GetMapping(value = "/{recipeName}")
  public Recipe fetchRecipeByName(@PathVariable(name = "recipeName") @NotBlank @Valid @Size(max = 10)  String recipeName) {
    log.info("Fetching Recipe by name: " + recipeName);
     return recipeManagerService.fetchRecipeByName(recipeName);
  }

  /**
   *
   * @return List<Recipe>
   */
  @ApiOperation(value = "List all recipes")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successful request"),
  })
  @RequestMapping(method = RequestMethod.GET)
  public List<Recipe> getRecipeList() {
    log.info("Getting the recipes");
    List<Recipe> list = recipeManagerService.getRecipeList();

    return list.stream()
        .map(Recipe::new)
        .collect(Collectors.toList());
  }


  /**
   *
   * @param requestRecipe
   * @return response with created recipe
   */
  @ApiOperation(value = "Create a recipe")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Recipe created"),
      @ApiResponse(code = 400, message = "Bad input")
  })
  @PostMapping(value = "/recipe")
  @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createRecipe(@RequestBody @Valid RequestRecipe requestRecipe){

    var recipeId=recipeManagerService.create(requestRecipe);
    log.info("Created Recipe: " + recipeId);
    return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Recipe created with id   %s", recipeId));
  }

  /**
   *
   * @param id
   * @return ResponseEntity<Object> DeleteRecipe
   */
  @ApiOperation(value = "Delete the recipe")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successful operation"),
      @ApiResponse(code = 400, message = "Invalid input"),
      @ApiResponse(code = 404, message = "Recipe not found by the given ID")
  })
  @DeleteMapping(value="/{id}")
  public ResponseEntity<Object> deleteRecipe(@PathVariable(name="id") @NotBlank Integer id){
    log.info("deleting recipe with given ID"+id);
    recipeManagerService.deleteRecipe(id);
return ResponseEntity.status(HttpStatus.NO_CONTENT).body(String.format("Successfully deleted recipe: "+id));
  }

  /**
   *
   * @param updateRecipeRequest
   * @return ResponseEntity<Object> updateRecipe
   */
  @ApiOperation(value = "Update the recipe")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Ingredient created"),
      @ApiResponse(code = 400, message = "Bad input")
  })
  @PatchMapping(value = "/recipeUpdate")
  public ResponseEntity<Object> updateRecipe(@Valid @RequestBody UpdateRecipeRequest updateRecipeRequest){
    log.info("Updating Recipe: ");
    recipeManagerService.update(updateRecipeRequest);
    return ResponseEntity.ok(String.format("Recipe updated successfully"));
  }

  /**
   *
   * @param search
   * @return ResponseEntity<CustomSearchResponse>
   */
  @ApiOperation(value = "Search recipes by given parameters")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successful request"),
      @ApiResponse(code = 404, message = "Different error messages related to criteria and recipe")
  })
  @GetMapping(value="/{search}")
  public List<Recipe> customSearch(@Valid @RequestParam("search")String[] search){
    return  recipeManagerService.search(search);
  }
}
