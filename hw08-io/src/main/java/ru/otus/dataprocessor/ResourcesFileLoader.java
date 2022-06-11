package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final URL resource;
    private final ObjectMapper mapper = new ObjectMapper();

    public ResourcesFileLoader(String fileName) {
        this.resource = ClassLoader.getSystemResource(fileName);
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат

        try {
            String text = Files.readString(Path.of(resource.toURI()));
            return mapper.readValue(text,
                    TypeFactory.defaultInstance().constructCollectionType(List.class, Measurement.class));
        }
        catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
