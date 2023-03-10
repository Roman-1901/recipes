package com.food.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Data
@AllArgsConstructor
public class Recipe {
    private final String name;
    private final int timeReady;
    private ArrayList<Ingredient> ingredients;
    private final ArrayList<String> steps;
}
