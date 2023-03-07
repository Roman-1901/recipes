package com.food.recipes.services;

import com.food.recipes.model.Ingredient;
import com.food.recipes.model.Recipe;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RecipesService {

    private final Map<Long, Recipe> recipeMap = new HashMap<>();
    private static long num = 0;

    //Добавление рецепта
    public String addRecipe(Recipe recipe) {
        long recipeNum = ++num;
        recipeMap.put(recipeNum, recipe);
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
            return "Рецепт удален";
        }
        return "Рецепт с данным id не найден";
    }

    //Поиск рецепта по id ингредиента
    public Optional<Recipe> getRecipeByIdIngredient (Long id) {
      return recipeMap.values().stream().filter(recipe -> recipe.getIngredients().containsKey(id)).findFirst();
    }

}
