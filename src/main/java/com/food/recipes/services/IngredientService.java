package com.food.recipes.services;
import com.food.recipes.model.Ingredient;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientService {
    protected final Map<Long, Ingredient> ingredientMap = new HashMap<>();
    private static long num = 0;

    //Добавление ингредиента
    public String addIngredient(Ingredient ingredient) {
        long id = ++num;
        ingredientMap.put(id, ingredient);
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
            return "Ингредиент удален";
        }
        return "Ингредиент с данным id не найден";
    }

}
