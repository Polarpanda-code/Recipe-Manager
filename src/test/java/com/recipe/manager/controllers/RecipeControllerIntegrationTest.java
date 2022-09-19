package com.recipe.manager.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.recipe.manager.dto.request.RequestRecipe;
import com.recipe.manager.model.Recipe;
import com.recipe.manager.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class RecipeControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Autowired
    private RecipeRepository recipeRepository;



    @Before
    public void before() {
        recipeRepository.deleteAll();
    }

    @Test
    public void test_createRecipe_successfully() throws Exception {
      RequestRecipe request = new RequestRecipe("pasta",
                "OTHER", 5, null, "someInstruction");

        MvcResult result = performPost("/api/v1/recipe", request)
                .andExpect(status().isCreated())
                .andReturn();

        Integer id = readByJsonPath(result, "$.id");
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        assertTrue(optionalRecipe.isPresent());
        assertEquals(optionalRecipe.get().getName(), request.getName());
    }

    @Test
    public void test_getRecipe_successfully() throws Exception {
        Recipe Recipe = RecipeTestDataBuilder.createRecipe();
        Recipe savedRecipe = recipeRepository.save(Recipe);

        performGet("/api/v1/recipe/" + savedRecipe.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedRecipe.getId()))
                .andExpect(jsonPath("$.name").value(savedRecipe.getName()))
                .andExpect(jsonPath("$.instructions").value(savedRecipe.getInstructions()))
                .andExpect(jsonPath("$.numberOfServings").value(savedRecipe.getNumberOfServings()));
    }

    @Test
    public void test_getRecipe_notFound() throws Exception {

        performGet("/api/v1/recipe/1")
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void test_listRecipe_successfully() throws Exception {
        Recipe recipe1 = new Recipe();
        recipe1.setId(5);
        recipe1.setName("name1");
        recipe1.setInstructions("Ins1");
        recipe1.setType("OTHER");

        Recipe recipe2 = new Recipe();
        recipe2.setId(6);
        recipe2.setName("name2");
        recipe2.setInstructions("Ins2");
        recipe2.setType("OTHER");

        List<Recipe> storedRecipeList = new ArrayList<>();
        storedRecipeList.add(recipe1);
        storedRecipeList.add(recipe2);

        recipeRepository.saveAll(storedRecipeList);

        MvcResult result = performGet("/api/v1/recipe")
                .andExpect(status().isOk())
                .andReturn();

        List<Recipe> RecipeList = getListFromMvcResult(result, Recipe.class);

        assertEquals(storedRecipeList.size(), RecipeList.size());
        assertEquals(storedRecipeList.get(0).getName(), RecipeList.get(0).getName());
        assertEquals(storedRecipeList.get(1).getName(), RecipeList.get(1).getName());
    }

    @Test
    public void test_updateRecipe_successfully() throws Exception {
        Recipe testRecipe = new Recipe();
        testRecipe.setName("lasagna");
        testRecipe.setType("OTHER");
        testRecipe.setInstructions("chop the onion, potato");
        testRecipe.setNumberOfServings(2);

        Recipe savedRecipe = recipeRepository.save(testRecipe);

        savedRecipe.setName("meat-lasagna");
        savedRecipe.setInstructions("add meat add pasta");

        performPatch("/api/v1/recipe", savedRecipe)
                .andExpect(status().isOk());

        Optional<Recipe> updatedRecipe = recipeRepository.findById(savedRecipe.getId());

        assertTrue(updatedRecipe.isPresent());
        assertEquals(savedRecipe.getName(), updatedRecipe.get().getName());
        assertEquals(savedRecipe.getNumberOfServings(), updatedRecipe.get().getNumberOfServings());
        assertEquals(savedRecipe.getInstructions(), updatedRecipe.get().getInstructions());
    }

    @Test
    public void test_updateRecipe_idIsNull() throws Exception {
        Recipe testRecipe = new Recipe();
        testRecipe.setName("sarmale");
        testRecipe.setInstructions("take grape leaf, take meat, cook them");
        testRecipe.setNumberOfServings(3);
        testRecipe.setType("OTHER");

        performPatch("/api/recipe/v1", testRecipe)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_updateRecipe_notFound() throws Exception {
        Recipe testRecipe = RecipeTestDataBuilder.createRecipe(1);

        performPatch("/api/recipe/v1", testRecipe)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void test_deleteRecipe_successfully() throws Exception {
        Recipe testRecipe = RecipeTestDataBuilder.createRecipe();
        Recipe savedRecipe = recipeRepository.save(testRecipe);

        performDelete("/api/recipe/v1", Pair.of("id", String.valueOf(savedRecipe.getId())))
                .andExpect(status().isOk());

        Optional<Recipe> deletedRecipe = recipeRepository.findById(savedRecipe.getId());

        assertTrue(deletedRecipe.isEmpty());
    }

    @Test
    public void test_deleteRecipe_notFound() throws Exception {
        performDelete("/api/v1/recipe", Pair.of("id", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void test_SearchRecipeByCriteria_fails() throws Exception {
        performPost("/api/recipe/v1/search", null)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").exists())
                .andReturn();
    }

}
