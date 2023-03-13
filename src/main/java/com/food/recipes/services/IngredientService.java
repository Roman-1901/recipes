package com.food.recipes.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.recipes.model.Ingredient;
import com.food.recipes.services.FileServices.FileService;
import com.food.recipes.services.FileServices.FileServiceIngredient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientService {
    private final FileServiceIngredient fileService;
    private Map<Long, Ingredient> ingredientMap = new HashMap<>();
    private static long num = 0;

    public IngredientService(FileServiceIngredient fileService) {
        this.fileService = fileService;
    }

    //Добавление ингредиента
    public String addIngredient(Ingredient ingredient) {
        long id = ++num;
        ingredientMap.put(id, ingredient);
        saveToFile();
        return "Ингредиент создан под номером " + id;
    }

    //Поиск ингредиента по id
    public Ingredient getIngredient(Long ingredientNum) {
        return ingredientMap.get(ingredientNum);
    }

    //Редактирование ингредиента по id
    public Ingredient editIngredient(Long id, Ingredient ingredient) {
        if (ingredientMap.containsKey(id)) {
            ingredientMap.put(id, ingredient);
            saveToFile();
            return ingredient;
        }
        return null;
    }

    //Вывод всех ингредиентов
    public Map<Long, Ingredient> getAllIngredients() {
        return ingredientMap;
    }

    //Удаление ингредиента по id
    public String removeIngredient(Long id) {
        if (ingredientMap.containsKey(id)) {
            ingredientMap.remove(id);
            saveToFile();
            return "Ингредиент удален";
        }
        return null;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

@PostConstruct
    private void readFromFile() {
        try {
            String json = fileService.readFromFile();
            ingredientMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Long, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
