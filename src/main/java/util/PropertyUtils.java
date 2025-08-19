package util;

import enums.EnvType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {
    private static final String ENV = "env";
    private static final String CONFIG_PROPERTIES = "_config.properties";
    private static final String INT_CONFIG_PROPERTIES = "local" + CONFIG_PROPERTIES;

    private static final String RESOURCES_PATH = System.getProperty("user.dir") + "/src/test/resources/";
    private Properties properties;
    private static PropertyUtils configLoader;

    public PropertyUtils() {
        String env = System.getProperty(ENV, EnvType.LOCAL.toString());

        switch (EnvType.valueOf(env)) {
            case LOCAL: {
                properties = getConfigPropertyFile(INT_CONFIG_PROPERTIES);
                break;
            }
            default: {
                throw new IllegalStateException("Invalid EnvType: " + env);
            }
        }
    }

    private Properties getConfigPropertyFile(String configFile) {
        return PropertyUtils.propertyLoader(RESOURCES_PATH + configFile);
    }

    public String getPropertyValue(String propertyKey) {
        String prop = properties.getProperty(propertyKey);
        if (prop != null) {
            return prop.trim();
        } else {
            throw new RuntimeException("Property " + propertyKey + " is not specified in the config.properties file");
        }
    }

    public static PropertyUtils getInstance() {
        if (configLoader == null) {
            configLoader = new PropertyUtils();
        }
        return configLoader;
    }

    public static Properties propertyLoader(String filePath) {
        Properties properties = new Properties();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            throw new RuntimeException("Properties file not found at: " + filePath);
        }
        try {
            properties.load(reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load the Properties file: " + filePath);
        }
        return properties;
    }

}
