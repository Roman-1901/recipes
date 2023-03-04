package com.food.recipes.services;
import com.food.recipes.model.Ingredient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class IngredientService {
    private final Map<Long, Ingredient> ingredientMap = new HashMap<>();
    private static long number = 0;


    public String addIngredient(Ingredient ingredient) {
        long ingredientNum = ++number;
        ingredientMap.put(ingredientNum, ingredient);
        return "Ингредиент создан под номером " + ingredientNum;
    }



    public Ingredient getIngredient(Long ingredientNum) {
        return ingredientMap.get(ingredientNum);
    }
}
