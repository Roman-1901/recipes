package com.food.recipes.services.FileServices;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class FileService {
    protected void save(String json, Path path) {
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
            Files.writeString(path, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String read(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
