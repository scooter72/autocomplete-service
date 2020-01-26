package server.config;

import java.io.IOException;
import java.util.Properties;

public class Configuration {
    private static Configuration instance;
    private final Properties properties = new Properties();

    private Configuration() {
        try {
            properties.load(
                    getClass().getClassLoader().getResourceAsStream("config.properties")
            );
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }
}
