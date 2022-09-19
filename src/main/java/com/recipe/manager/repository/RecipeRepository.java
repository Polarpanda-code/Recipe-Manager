package com.recipe.manager.repository;


import com.recipe.manager.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


// repo class with native query implementation
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

  /**
   *
   * @param name
   * @return
   */
  @Query(value = "SELECT * FROM RECIPES  WHERE name = :name ", nativeQuery = true)
  Recipe findRecipeByName( String name);

  /**
   *
   * @param recipeType
   * @param ingredients
   * @param instructions
   * @param numberOfServings
   * @return
   */
  @Query(value = "SELECT * FROM RECIPES r, INGREDIENTS i WHERE (r.type = :recipeType OR r.instructions LIKE %instructions% OR r.number_of_serving= :numberOfServings OR i.ingredient_name= :ingredients) AND i.recipe_id=r.recipe_id ", nativeQuery = true)
  List<Recipe> findByCustomSearch( String recipeType,String ingredients, String instructions, Integer numberOfServings);



}