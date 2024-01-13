package utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;

/**
 * Utility class for loading application configuration from a properties file
 */
public class AppConfigurationLoader {

    private static Properties properties;

    private static Properties getProperties() {
        if (properties == null) {
            try {
                readAndLoadConfig();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    /**
     * Get the property value associated with the specified key
     *
     * @param key The key whose associated value is to be returned
     * @return The value associated with the specified key, or null if the key is not found
     */
    public static String getProperty(String key) {
        return getProperties().getProperty(key);
    }

    private static void readAndLoadConfig() {
        properties = new Properties();
        try(FileReader reader = new FileReader(Objects.requireNonNull(getFile()))) {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException("Configuration file not loaded!");
        }
    }

    /**
     * Retrieve the configuration file as a File object
     *
     * @return A File object representing the configuration file
     * @throws IllegalArgumentException If the configuration file is not found
     */
    private static File getFile() {
        URL resource = AppConfigurationLoader.class.getClassLoader().getResource("/config.properties");
        if (resource == null) {
            throw new IllegalArgumentException("Configuration file not found!");
        }
        else {
            try {
                return new File(resource.toURI());
            } catch (URISyntaxException e) {
                throw new RuntimeException("Error converting URL to URI", e);
            }
        }
    }
}