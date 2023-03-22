package com.food.recipes.services.FileServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
@Service
public class FileServiceRecipe extends FileService {
    @Value("${path.to.file.folder}")
    private String fileFolder;

    @Value("${path.of.file.name.recipe}")
    private String fileName;

    public void saveToFile(String json) {
        save(json, Path.of(fileFolder, fileName));
    }

    public String readFromFile() {
        return read(Path.of(fileFolder, fileName));
    }

    public File getDataFile() {
        return getFile(fileFolder, fileName);
    }

    public void cleanFile() {
        clean(Path.of(fileFolder, fileName));
    }

    public Path createTempFile(String suffix) {
        try {
            return Files.createTempFile(Path.of(fileFolder), "tempFile", suffix);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
