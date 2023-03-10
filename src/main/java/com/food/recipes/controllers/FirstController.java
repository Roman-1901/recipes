package com.food.recipes.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Hidden
@Tag(name = "Тестовый первый контроллер", description = "")
public class FirstController {
    @GetMapping
    public String startWeb() {
        return "Приложение запущено";
    }

    @GetMapping("/info")
    public String information() {
        return "Имя ученика: Насибуллин Роман Ильсурович; Название проекта: Рецепты еды; Дата создания: 25.02.2023; Описание: программа по добавлению, редактированию и выводу информации по рецептам блюд";
    }
}
