package com.food.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;


@Data
@AllArgsConstructor
public class Recipe {
    private final String name;
    private final int timeReady;
    private final ArrayList<Ingredient> ingredients;
    private final ArrayList<String> steps;
}
