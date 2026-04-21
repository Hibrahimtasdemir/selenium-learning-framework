package base;

import static io.restassured.RestAssured.given;

import api.AuthClient;
import com.atlassian.oai.validator.restassured.OpenApiValidationFilter;
import config.FrameworkConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import java.nio.file.Files;
import java.nio.file.Path;
import org.testng.annotations.BeforeClass;

public class BaseApiTest {

  protected RequestSpecification requestSpecification;

  @BeforeClass(alwaysRun = true)
  public void setUpApiClient() {
    RequestSpecBuilder builder =
        new RequestSpecBuilder()
            .setBaseUri(FrameworkConfig.getApiBaseUrl())
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json");

    applyContractValidation(builder);
    applyAuthentication(builder);
    requestSpecification = builder.build();
  }

  protected RequestSpecification apiRequest() {
    return given().spec(requestSpecification);
  }

  private void applyAuthentication(RequestSpecBuilder builder) {
    String authType = FrameworkConfig.getApiAuthType();

    switch (authType) {
      case "none" -> {
        return;
      }
      case "bearer" -> applyBearerAuthentication(builder);
      case "login" -> applyLoginAuthentication(builder);
      case "api_key" -> applyApiKeyAuthentication(builder);
      default -> throw new IllegalArgumentException("Unsupported api.auth.type: " + authType);
    }
  }

  private void applyContractValidation(RequestSpecBuilder builder) {
    if (!FrameworkConfig.isApiContractValidationEnabled()) {
      return;
    }

    String configuredSpecPath = FrameworkConfig.getApiContractOpenApiSpecPath();
    Path openApiSpecPath = Path.of(configuredSpecPath).toAbsolutePath().normalize();

    if (!Files.exists(openApiSpecPath)) {
      throw new IllegalStateException(
          "OpenAPI spec file not found: " + openApiSpecPath + ". Update api.contract.openapi.spec.path.");
    }

    builder.addFilter(new OpenApiValidationFilter(openApiSpecPath.toString()));
  }

  private void applyBearerAuthentication(RequestSpecBuilder builder) {
    String token = FrameworkConfig.getApiAuthToken();

    if (token.isBlank()) {
      throw new IllegalStateException("api.auth.token must be set when api.auth.type=bearer");
    }

    addAuthorizationHeader(builder, token);
  }

  private void applyLoginAuthentication(RequestSpecBuilder builder) {
    String token = AuthClient.getAccessToken();
    addAuthorizationHeader(builder, token);
  }

  private void applyApiKeyAuthentication(RequestSpecBuilder builder) {
    String keyName = FrameworkConfig.getApiKeyName();
    String keyValue = FrameworkConfig.getApiKeyValue();

    if (keyName.isBlank() || keyValue.isBlank()) {
      throw new IllegalStateException(
          "api.auth.key.name and api.auth.key.value must be set when api.auth.type=api_key");
    }

    String keyPlacement = FrameworkConfig.getApiKeyPlacement();
    switch (keyPlacement) {
      case "header" -> builder.addHeader(keyName, keyValue);
      case "query" -> builder.addQueryParam(keyName, keyValue);
      default -> throw new IllegalArgumentException("Unsupported api.auth.key.in: " + keyPlacement);
    }
  }

  private void addAuthorizationHeader(RequestSpecBuilder builder, String token) {
    String scheme = FrameworkConfig.getApiAuthScheme();
    String headerValue = scheme.isBlank() ? token : scheme + " " + token;
    builder.addHeader(FrameworkConfig.getApiAuthHeaderName(), headerValue);
  }
}
