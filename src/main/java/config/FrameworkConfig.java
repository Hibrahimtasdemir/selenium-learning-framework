package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public final class FrameworkConfig {

  private static final Properties PROPERTIES = loadProperties();

  private FrameworkConfig() {}

  public static String getBaseUrl() {
    String baseUrl = get("base.url", "https://the-internet.herokuapp.com");
    return baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
  }

  public static String getApiBaseUrl() {
    String apiBaseUrl =
        getWithEnvironmentFallback(
            "api.base.url", "API_BASE_URL", "https://jsonplaceholder.typicode.com");
    return apiBaseUrl.endsWith("/")
        ? apiBaseUrl.substring(0, apiBaseUrl.length() - 1)
        : apiBaseUrl;
  }

  public static String getApiAuthType() {
    return getWithEnvironmentFallback("api.auth.type", "API_AUTH_TYPE", "none").toLowerCase();
  }

  public static String getApiAuthToken() {
    return getWithEnvironmentFallback("api.auth.token", "API_AUTH_TOKEN", "");
  }

  public static String getApiKeyName() {
    return getWithEnvironmentFallback("api.auth.key.name", "API_AUTH_KEY_NAME", "X-API-Key");
  }

  public static String getApiKeyValue() {
    return getWithEnvironmentFallback("api.auth.key.value", "API_AUTH_KEY_VALUE", "");
  }

  public static String getApiKeyPlacement() {
    return getWithEnvironmentFallback("api.auth.key.in", "API_AUTH_KEY_IN", "header")
        .toLowerCase();
  }

  public static String getApiUsersPath() {
    return getWithEnvironmentFallback("api.users.path", "API_USERS_PATH", "/users");
  }

  public static String getApiOrdersPath() {
    return getWithEnvironmentFallback("api.orders.path", "API_ORDERS_PATH", "/posts");
  }

  public static boolean isApiContractValidationEnabled() {
    return Boolean.parseBoolean(
        getWithEnvironmentFallback(
            "api.contract.validation.enabled", "API_CONTRACT_VALIDATION_ENABLED", "false"));
  }

  public static String getApiContractOpenApiSpecPath() {
    return getWithEnvironmentFallback(
        "api.contract.openapi.spec.path",
        "API_CONTRACT_OPENAPI_SPEC_PATH",
        "openapi/openapi-template.yaml");
  }

  public static int getApiUsersSampleId() {
    return Integer.parseInt(getWithEnvironmentFallback("api.users.sample.id", "API_USERS_SAMPLE_ID", "1"));
  }

  public static int getApiOrdersSampleId() {
    return Integer.parseInt(
        getWithEnvironmentFallback("api.orders.sample.id", "API_ORDERS_SAMPLE_ID", "1"));
  }

  public static String getApiUsersIdField() {
    return getWithEnvironmentFallback("api.users.id.field", "API_USERS_ID_FIELD", "id");
  }

  public static String getApiOrdersIdField() {
    return getWithEnvironmentFallback("api.orders.id.field", "API_ORDERS_ID_FIELD", "id");
  }

  public static List<String> getApiUsersEchoFields() {
    return parseCsv(
        getWithEnvironmentFallback(
            "api.users.assert.echo.fields", "API_USERS_ASSERT_ECHO_FIELDS", "name,username,email"));
  }

  public static List<String> getApiOrdersEchoFields() {
    return parseCsv(
        getWithEnvironmentFallback(
            "api.orders.assert.echo.fields", "API_ORDERS_ASSERT_ECHO_FIELDS", "orderNumber,status,amount"));
  }

  public static String getApiUserResponseSchemaPath() {
    return getWithEnvironmentFallback(
        "api.users.response.schema.path",
        "API_USERS_RESPONSE_SCHEMA_PATH",
        "schemas/users/user-response.schema.json");
  }

  public static String getApiOrderResponseSchemaPath() {
    return getWithEnvironmentFallback(
        "api.orders.response.schema.path",
        "API_ORDERS_RESPONSE_SCHEMA_PATH",
        "schemas/orders/order-response.schema.json");
  }

  public static String getApiDeleteResponseSchemaPath() {
    return getWithEnvironmentFallback(
        "api.delete.response.schema.path",
        "API_DELETE_RESPONSE_SCHEMA_PATH",
        "schemas/posts/delete-response.schema.json");
  }

  public static String getApiUsersCreatePayloadPath() {
    return getWithEnvironmentFallback(
        "api.users.create.payload.path",
        "API_USERS_CREATE_PAYLOAD_PATH",
        "payloads/users/create-user.json");
  }

  public static String getApiUsersUpdatePayloadPath() {
    return getWithEnvironmentFallback(
        "api.users.update.payload.path",
        "API_USERS_UPDATE_PAYLOAD_PATH",
        "payloads/users/update-user.json");
  }

  public static String getApiOrdersCreatePayloadPath() {
    return getWithEnvironmentFallback(
        "api.orders.create.payload.path",
        "API_ORDERS_CREATE_PAYLOAD_PATH",
        "payloads/orders/create-order.json");
  }

  public static String getApiOrdersUpdatePayloadPath() {
    return getWithEnvironmentFallback(
        "api.orders.update.payload.path",
        "API_ORDERS_UPDATE_PAYLOAD_PATH",
        "payloads/orders/update-order.json");
  }

  public static String getApiLoginPath() {
    return getWithEnvironmentFallback("api.auth.login.path", "API_AUTH_LOGIN_PATH", "/auth/login");
  }

  public static String getApiLoginUsernameField() {
    return getWithEnvironmentFallback(
        "api.auth.login.username.field", "API_AUTH_LOGIN_USERNAME_FIELD", "username");
  }

  public static String getApiLoginPasswordField() {
    return getWithEnvironmentFallback(
        "api.auth.login.password.field", "API_AUTH_LOGIN_PASSWORD_FIELD", "password");
  }

  public static String getApiLoginUsername() {
    return getWithEnvironmentFallback("api.auth.login.username", "API_AUTH_LOGIN_USERNAME", "");
  }

  public static String getApiLoginPassword() {
    return getWithEnvironmentFallback("api.auth.login.password", "API_AUTH_LOGIN_PASSWORD", "");
  }

  public static String getApiLoginTokenJsonPath() {
    return getWithEnvironmentFallback(
        "api.auth.login.token.json.path", "API_AUTH_LOGIN_TOKEN_JSON_PATH", "token");
  }

  public static String getApiAuthHeaderName() {
    return getWithEnvironmentFallback(
        "api.auth.header.name", "API_AUTH_HEADER_NAME", "Authorization");
  }

  public static String getApiAuthScheme() {
    return getWithEnvironmentFallback("api.auth.scheme", "API_AUTH_SCHEME", "Bearer");
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

  private static String getWithEnvironmentFallback(
      String propertyKey, String environmentKey, String defaultValue) {
    String systemPropertyValue = System.getProperty(propertyKey);
    if (systemPropertyValue != null) {
      return systemPropertyValue;
    }

    String environmentValue = System.getenv(environmentKey);
    if (environmentValue != null && !environmentValue.isBlank()) {
      return environmentValue;
    }

    return PROPERTIES.getProperty(propertyKey, defaultValue);
  }

  private static List<String> parseCsv(String value) {
    return Arrays.stream(value.split(","))
        .map(String::trim)
        .filter(entry -> !entry.isEmpty())
        .toList();
  }

  private static Properties loadProperties() {
    Properties properties = new Properties();

    try (InputStream inputStream =
        FrameworkConfig.class.getClassLoader().getResourceAsStream("framework.properties")) {
      if (inputStream != null) {
        properties.load(inputStream);
      }
    } catch (IOException e) {
      throw new RuntimeException("Could not load framework.properties file.", e);
    }

    return properties;
  }
}
