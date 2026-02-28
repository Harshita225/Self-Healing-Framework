package storage;

import model.ElementMetadata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LocatorStorage {

    private static final String FILE_PATH = "healing-data.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void save(String key, ElementMetadata metadata) {

        try {
            Map<String, ElementMetadata> data = loadAll();
            data.put(key, metadata);
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(FILE_PATH), data);

            System.out.println("Metadata saved to JSON.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ElementMetadata get(String key) {

        try {
            Map<String, ElementMetadata> data = loadAll();
            return data.get(key);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Map<String, ElementMetadata> loadAll() {

        try {
            File file = new File(FILE_PATH);

            if (!file.exists()) {
                return new HashMap<>();
            }

            return mapper.readValue(
                    file,
                    new TypeReference<Map<String, ElementMetadata>>() {}
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }
}