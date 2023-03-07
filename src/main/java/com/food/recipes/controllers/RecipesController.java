package com.food.recipes.controllers;

import com.food.recipes.model.Recipe;
import com.food.recipes.services.RecipesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/recipe")
public class RecipesController {
    private final RecipesService recipesService;

    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    //Поиск рецепта по id
    @GetMapping("/{recipeNum}")
    public ResponseEntity<Recipe> recipeGet(@PathVariable long recipeNum) {
        Recipe recipe = recipesService.getRecipe(recipeNum);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    //Поиск рецепта по id ингредиента
    @GetMapping("/by/")
    public ResponseEntity<Optional<Recipe>> recipeGetByIdIngredient(@RequestParam long ingredient) {
        Optional<Recipe> recipe = recipesService.getRecipeByIdIngredient(ingredient);
        if (recipe.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }

    //Создание рецепта
    @PostMapping()
    public ResponseEntity<String> createRecipe(@RequestBody Recipe recipe) {
        String create = recipesService.addRecipe(recipe);
        return ResponseEntity.ok().body(create);
    }

    //Редактирование рецепта
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> putRecipe(@PathVariable long id, @RequestBody Recipe recipe) {
        Recipe putRecipe = recipesService.editRecipe(id, recipe);
        if (putRecipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(putRecipe);
    }

    //Вывод всех рецептов
    @GetMapping()
    public ResponseEntity<Map<Long, Recipe>> showRecipes() {
        Map<Long, Recipe> recipes = recipesService.getAllRecipes();
        if (recipes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipes);
    }

    //Удаление рецепта по id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable long id) {
        String delete = recipesService.removeRecipe(id);
        return ResponseEntity.ok().body(delete);
    }

}
