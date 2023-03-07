package com.food.recipes.services;
import com.food.recipes.model.Recipe;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;


@Service
public class RecipesService {

    private final Map<Long, Recipe> recipeMap = new HashMap<>();
    private static long number = 0;


    public String addRecipe(Recipe recipe) {
        long recipeNum = ++number;
        recipeMap.put(recipeNum, recipe);
        return "Рецепт создан под номером " + recipeNum;
    }



    public Recipe getRecipe(Long recipeNum) {
        return recipeMap.get(recipeNum);
    }
}
