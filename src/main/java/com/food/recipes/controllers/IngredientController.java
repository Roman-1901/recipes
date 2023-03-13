package com.food.recipes.controllers;

import com.food.recipes.model.Ingredient;
import com.food.recipes.services.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ингредиенты", description = "Добавление, удаление, редактирование ингредиентов")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    //Поиск ингредиента по id
    @GetMapping("/{ingredientNum}")
    @Operation(summary = "Поиск ингредиента по id",
            description = "Необходимо указать id ингредиента для поиска")
    @Parameters(
            value = {
                    @Parameter(
                            name = "ingredientNum", example = "1"
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    }
    )
    public ResponseEntity<Ingredient> recipeGet(@PathVariable long ingredientNum) {
        Ingredient ingredient = ingredientService.getIngredient(ingredientNum);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }


    //Вывод всех ингредиентов
    @GetMapping
    @Operation(summary = "Вывод всех ингредиентов")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    }
    )
    public ResponseEntity<Map<Long, Ingredient>> showIngredients() {
        Map<Long, Ingredient> getIngredients = ingredientService.getAllIngredients();
        if (getIngredients == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(getIngredients);
    }


    //Создание нового ингредиента
    @PostMapping()
    @Operation(summary = "Создание нового ингредиента")
    public ResponseEntity<String> createIngredient(@RequestBody Ingredient ingredient) {
        String create = ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok(create);
    }

    //Редактирование ингредиента
    @PutMapping("/{id}")
    @Operation(summary = "Редактирование ингредиента", description = "Необходимо указать номер id")
    @Parameters(
            value = {
                    @Parameter(
                            name = "id", example = "1"
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    }
    )
    public ResponseEntity<Ingredient> putIngredient(@PathVariable long id, @RequestBody Ingredient ingredient) {
        Ingredient putIngredient = ingredientService.editIngredient(id, ingredient);
        if (putIngredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    //Удаление ингредиента
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление ингредиента", description = "Необходимо указать номер id")
    @Parameters(
            value = {
                    @Parameter(
                            name = "id", example = "1"
                    )
            }
    )
    public ResponseEntity<String> deleteIngredient(@PathVariable long id) {
        String delete = ingredientService.removeIngredient(id);
        if (delete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(delete);
    }
}
