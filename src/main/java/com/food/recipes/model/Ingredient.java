package com.food.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Ingredient {
    private final String name;
    private final int count;
    private final String measureUnit;
}

