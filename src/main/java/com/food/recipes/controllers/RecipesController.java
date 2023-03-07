package com.food.recipes.controllers;
import com.food.recipes.model.Recipe;
import com.food.recipes.services.RecipesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/recipe")
public class RecipesController {
    private RecipesService recipesService;

    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }


    @GetMapping("/get/{recipeNum}")
    public ResponseEntity<Recipe> recipeGet(@PathVariable long recipeNum) {
        Recipe recipe = recipesService.getRecipe(recipeNum);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    @PostMapping("/create")
    public String createRecipe(@RequestBody Recipe recipe) {
        return recipesService.addRecipe(recipe);
    }

}
