package com.shilov.common.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    public static final String RESERVATION_STORAGE_PATH = "reservations.path";
    public static final String SPACE_STORAGE_PATH = "spaces.path";
    public static final String GOODBYE_WRITER_CLASS_FILE_PATH = "goodbyeWriter.path";

    public static final String DB_URL = "db.url";
    public static final String DB_USER = "db.user";
    public static final String DB_PASSWORD = "db.password";

    private PropertyReader() {}

    public  static String getProperty(String propertyName) throws IOException {
        return loadProperties().getProperty(propertyName);
    }

    private static Properties loadProperties() throws IOException{
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(getConfigPath())) {
            properties.load(fis);
        }
        return properties;
    }

    private static String getConfigPath() {
        String key = "configFilePath";
        return System.getProperty(key).trim();
    }
}
