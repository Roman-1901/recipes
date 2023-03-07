package com.food.recipes.controllers;
import com.food.recipes.model.Ingredient;
import com.food.recipes.services.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    //Поиск ингредиента по id
    @GetMapping("/{ingredientNum}")
    public ResponseEntity<Ingredient> recipeGet(@PathVariable long ingredientNum) {
        Ingredient ingredient = ingredientService.getIngredient(ingredientNum);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    //Вывод всех ингредиентов
    @GetMapping
    public ResponseEntity<Map<Long, Ingredient>> showIngredients() {
       Map<Long, Ingredient> getIngredients = ingredientService.getAllIngredients();
        if (getIngredients == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(getIngredients);
    }

    //Создание нового ингредиента
    @PostMapping()
    public ResponseEntity<String> createIngredient(@RequestBody Ingredient ingredient) {
        String create = ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok().body(create);
    }

    //Редактирование ингредиента
    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> putIngredient(@PathVariable long id, @RequestBody Ingredient ingredient) {
        Ingredient putIngredient = ingredientService.editIngredient(id, ingredient);
        if (putIngredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    //Удаление ингредиента
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIngredient(@PathVariable long id) {
        String delete = ingredientService.removeIngredient(id);
        return ResponseEntity.ok().body(delete);
    }
}
