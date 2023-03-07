package com.food.recipes.controllers;
import com.food.recipes.model.Ingredient;
import com.food.recipes.services.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/get/{ingredientNum}")
    public ResponseEntity<Ingredient> recipeGet(@PathVariable long ingredientNum) {
        Ingredient ingredient = ingredientService.getIngredient(ingredientNum);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @PostMapping("/create")
    public String createRecipe(@RequestBody Ingredient ingredient) {
        return ingredientService.addIngredient(ingredient);
    }

}
