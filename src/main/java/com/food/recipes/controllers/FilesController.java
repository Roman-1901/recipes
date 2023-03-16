package com.food.recipes.controllers;

import com.food.recipes.services.FileServices.FileServiceIngredient;
import com.food.recipes.services.FileServices.FileServiceRecipe;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files")
public class FilesController {
    private final FileServiceRecipe fileServiceRecipe;
    private final FileServiceIngredient fileServiceIngredient;

    public FilesController(FileServiceRecipe fileService, FileServiceIngredient fileServiceIngredient) {
        this.fileServiceRecipe = fileService;
        this.fileServiceIngredient = fileServiceIngredient;
    }


    @GetMapping(value = "/export/recipe")
    public ResponseEntity<InputStreamResource> downloadFile() throws FileNotFoundException {
        File file = fileServiceRecipe.getDataFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = \"Recipes.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/import/recipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
