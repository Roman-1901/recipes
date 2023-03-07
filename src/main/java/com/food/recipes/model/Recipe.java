package com.food.recipes.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Data
public class Recipe {
    private final String name;
    private final int timeReady;
    private Map<Long, Ingredient> ingredients = new HashMap<>();
    private final ArrayList<String> steps;
    private static long id = 0;

    public Recipe(String name, int timeReady, ArrayList<Ingredient> ingredients, ArrayList<String> steps) {
        this.name = name;
        this.timeReady = timeReady;
        for (Ingredient ingredient : ingredients) {
            this.ingredients.put(++id, ingredient);
        }
        this.steps = steps;
    }
}
