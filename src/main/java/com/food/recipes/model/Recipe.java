package com.food.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    private String name;
    private int timeReady;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> steps;

    @Override
    public String toString() {
        return  name + "\n" +
                "Время приготовления: " + timeReady + "\n" +
                "Ингредиенты: \n" + ingredients + "\n" +
                "Инструкция приготовления: \n" + steps + "\n" + "\n";
    }
}
