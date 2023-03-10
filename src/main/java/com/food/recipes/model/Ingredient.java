package com.food.recipes.model;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Data
@AllArgsConstructor
public class Ingredient {
    private final String name;
    private final int count;
    private final String measureUnit;
}



