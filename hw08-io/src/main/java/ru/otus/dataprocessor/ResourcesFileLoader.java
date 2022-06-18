package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final URL resource;
    private final Gson gson = new Gson();

    public ResourcesFileLoader(String fileName) {
        this.resource = ClassLoader.getSystemResource(fileName);
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат

        try {
            String text = Files.readString(Path.of(resource.toURI()));

            Type listType = new TypeReference<List<Measurement>>() {}.getType();
            return gson.fromJson(text, listType);
        }
        catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
