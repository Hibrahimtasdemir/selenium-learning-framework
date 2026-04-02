package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class FrameworkConfig {

    private static final Properties PROPERTIES = loadProperties();

    private FrameworkConfig() {
    }

    public static String getBaseUrl() {
        String baseUrl = get("base.url", "https://the-internet.herokuapp.com");
        return baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }

    public static String getBrowser() {
        return get("browser", "chrome").toLowerCase();
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(get("headless", "false"));
    }

    public static long getDemoPauseMs() {
        return Long.parseLong(get("demo.pause.ms", "1000"));
    }

    public static int getRetryCount() {
        return Integer.parseInt(get("retry.count", "1"));
    }

    public static String getReportDirectory() {
        return get("report.directory", "test-output/report");
    }

    public static String getReportFileName() {
        return get("report.file.name", "automation-report.html");
    }

    private static String get(String key, String defaultValue) {
        return System.getProperty(key, PROPERTIES.getProperty(key, defaultValue));
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();

        try (InputStream inputStream = FrameworkConfig.class.getClassLoader().getResourceAsStream("framework.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not load framework.properties file.", e);
        }

        return properties;
    }
}
