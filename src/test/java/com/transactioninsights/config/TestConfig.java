package com.transactioninsights.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {
    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = TestConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getAppUrl() {
        return properties.getProperty("baseUrl", "http://localhost:3000/");
    }

    public static int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicit.wait", "10"));
    }

    public static int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicit.wait", "10"));
    }

    public static int getRetryCount() {
        return Integer.parseInt(properties.getProperty("retry.count", "2"));
    }

    public static String getReportPath() {
        return properties.getProperty("report.path", "test-output/extent-report.html");
    }

    public static boolean isScreenshotOnFailureEnabled() {
        return Boolean.parseBoolean(properties.getProperty("screenshot.on.failure", "true"));
    }
}
