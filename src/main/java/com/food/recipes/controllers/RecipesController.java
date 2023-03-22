package com.food.recipes.controllers;

import com.food.recipes.model.Ingredient;
import com.food.recipes.model.Recipe;
import com.food.recipes.services.FileServices.FileServiceRecipe;
import com.food.recipes.services.RecipesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты", description = "Добавление, удаление, редактирование рецептов")
public class RecipesController {
    private final RecipesService recipesService;

    public RecipesController(RecipesService recipesService, FileServiceRecipe fileServiceRecipe) {
        this.recipesService = recipesService;
    }

    //Поиск рецепта по id
    @GetMapping("/{recipeNum}")
    @Operation(summary = "Поиск рецепта по id",
            description = "Необходимо указать id рецепта для поиска")
    @Parameters(
            value = {
                    @Parameter(
                            name = "recipeNum", example = "1"
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Страница отсутствует"
            )
    }
    )
    public ResponseEntity<Recipe> recipeGet(@PathVariable long recipeNum) {
        Recipe recipe = recipesService.getRecipe(recipeNum);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }


    //Создание рецепта
    @PostMapping()
    @Operation(summary = "Создание нового рецепта")
    public ResponseEntity<String> createRecipe(@RequestBody Recipe recipe) {
        String create = recipesService.addRecipe(recipe);
        return ResponseEntity.ok().body(create);
    }


    //Редактирование рецепта
    @PutMapping("/{id}")
    @Operation(summary = "Редактирование рецепта", description = "Необходимо указать номер id")
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
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    }
    )
    public ResponseEntity<Recipe> putRecipe(@PathVariable long id, @RequestBody Recipe recipe) {
        Recipe putRecipe = recipesService.editRecipe(id, recipe);
        if (putRecipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(putRecipe);
    }


    //Вывод всех рецептов
    @GetMapping()
    @Operation(summary = "Вывод всех рецептов")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    }
    )


    public ResponseEntity<Map<Long, Recipe>> showRecipes() {
        Map<Long, Recipe> recipes = recipesService.getAllRecipes();
        if (recipes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipes);
    }


    //Удаление рецепта по id
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление рецепта", description = "Необходимо указать номер id")
    @Parameters(
            value = {
                    @Parameter(
                            name = "id", example = "1"
                    )
            }
    )
    public ResponseEntity<String> deleteRecipe(@PathVariable long id) {
        String delete = recipesService.removeRecipe(id);
        if (delete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(delete);
    }




    }
