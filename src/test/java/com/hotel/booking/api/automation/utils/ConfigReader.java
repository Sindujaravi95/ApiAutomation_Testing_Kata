package com.hotel.booking.api.automation.utils;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties;
    private static final String CONFIG_PATH =
            "src/test/resources/config/config.properties";

    static {
        try {
            FileInputStream fis = new FileInputStream(
                    System.getProperty("user.dir")
                            + "/src/test/resources/config/config.properties"
            );
            properties = new Properties();
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file");
        }
    }

    private ConfigReader() {
        // prevent instantiation
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    // Write value
    public static void set(String key, String value) {
        try {
            properties.setProperty(key, value);
            FileOutputStream fos = new FileOutputStream(CONFIG_PATH);
            properties.store(fos, "Updated token");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

