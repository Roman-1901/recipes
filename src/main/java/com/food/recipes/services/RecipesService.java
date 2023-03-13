package com.food.recipes.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.recipes.model.Recipe;
import com.food.recipes.services.FileServices.FileServiceRecipe;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


@Service
public class RecipesService {

    private Map<Long, Recipe> recipeMap = new HashMap<>();
    private static long num = 0;

    private final FileServiceRecipe fileService;

    public RecipesService(FileServiceRecipe fileService) {
        this.fileService = fileService;
    }

    //Добавление рецепта
    public String addRecipe(Recipe recipe) {
        long recipeNum = ++num;
        recipeMap.put(recipeNum, recipe);
        saveToFile();
        return "Рецепт создан под номером " + recipeNum;
    }

    //Поиск рецепта по id
    public Recipe getRecipe(Long recipeNum) {
        return recipeMap.get(recipeNum);
    }

    //Редактирование рецепта
    public Recipe editRecipe(Long id, Recipe recipe) {
        if (recipeMap.containsKey(id)) {
            recipeMap.put(id, recipe);
            saveToFile();
            return recipe;
        }
        return null;
    }

    //Вывод всех рецептов
    public Map<Long, Recipe> getAllRecipes() {
        return recipeMap;
    }

    //Удаление рецепта по id
    public String removeRecipe(Long id) {
        if (recipeMap.containsKey(id)) {
            recipeMap.remove(id);
            saveToFile();
            return "Рецепт удален";
        }
        return null;
    }


    //Поиск рецепта по id ингредиента
//    public Optional<Recipe> getRecipeByIdIngredient(Long id) {
//        return recipeMap.values().stream().filter(recipe -> recipe.getIngredients().containsKey(id)).findFirst();
//    }


    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

@PostConstruct
    private void readFromFile() {
        try {
            String json = fileService.readFromFile();
            recipeMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Long, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
