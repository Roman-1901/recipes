package com.food.recipes.controllers;

import com.food.recipes.model.Recipe;
import com.food.recipes.services.FileServices.FileServiceIngredient;
import com.food.recipes.services.FileServices.FileServiceRecipe;
import com.food.recipes.services.RecipesService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/files")
@Tag(name = "Файлы", description = "Импорт/Экспорт файлов")
public class FilesController {
    private final FileServiceRecipe fileServiceRecipe;
    private final FileServiceIngredient fileServiceIngredient;
    private final RecipesService recipesService;

    public FilesController(FileServiceRecipe fileService, FileServiceIngredient fileServiceIngredient, RecipesService recipesService) {
        this.fileServiceRecipe = fileService;
        this.fileServiceIngredient = fileServiceIngredient;
        this.recipesService = recipesService;
    }


    @GetMapping(value = "/export/recipe")
    @Operation(summary = "Экспорт файла рецептов в формате .json")
    public ResponseEntity<InputStreamResource> downloadFile() throws FileNotFoundException {
        File file = fileServiceRecipe.getDataFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Recipes.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/export/report")
    @Operation(summary = "Экспорт файла рецептов в формате .txt")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "всё хорошо, запрос выполнился"
            ),
            @ApiResponse(
                    responseCode = "400", description = "есть ошибка в параметрах запроса"
            ),
            @ApiResponse(
                    responseCode = "404", description = "URL неверный или такого действия нет в веб-приложении"
            ),
            @ApiResponse(
                    responseCode = "500", description = "во время выполнения запроса произошла ошибка на сервере"
            )
    }
    )
    public ResponseEntity<Object> downloadFileTXt() {
        try {
            Path path = recipesService.saveTxt();
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.txt\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }


    @PostMapping(value = "/import/recipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Импорт файла рецептов в формате .json")
    public ResponseEntity<Void> uploadFile(@RequestParam MultipartFile multipartFile) {
        fileServiceRecipe.cleanFile();
        File file = fileServiceRecipe.getDataFile();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            IOUtils.copy(multipartFile.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(value = "/import/ingredient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Импорт файла ингредиентов в формате .json")
    public ResponseEntity<Void> uploadFileIngredient(@RequestParam MultipartFile multipartFile) {
        fileServiceIngredient.cleanFile();
        File file = fileServiceIngredient.getDataFile();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            IOUtils.copy(multipartFile.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
