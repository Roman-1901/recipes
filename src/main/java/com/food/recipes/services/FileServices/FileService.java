package com.food.recipes.services.FileServices;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class FileService {
    protected void save(String json, Path path) {
        clean(path);
        try {
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

    protected File getFile(String filePath, String fileName) {
        return new File (filePath + "/" + fileName);
    }

    protected void clean(Path path) {
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
