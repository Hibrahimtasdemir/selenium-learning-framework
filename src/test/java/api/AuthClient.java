package api;

import static io.restassured.RestAssured.given;

import config.FrameworkConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

public final class AuthClient {

  private static volatile String cachedToken;

  private AuthClient() {}

  public static String getAccessToken() {
    if (cachedToken != null && !cachedToken.isBlank()) {
      return cachedToken;
    }

    synchronized (AuthClient.class) {
      if (cachedToken == null || cachedToken.isBlank()) {
        cachedToken = loginAndExtractToken();
      }
      return cachedToken;
    }
  }

  public static void clearCachedToken() {
    synchronized (AuthClient.class) {
      cachedToken = null;
    }
  }

  private static String loginAndExtractToken() {
    String username = FrameworkConfig.getApiLoginUsername();
    String password = FrameworkConfig.getApiLoginPassword();

    if (username.isBlank() || password.isBlank()) {
      throw new IllegalStateException(
          "api.auth.login.username and api.auth.login.password must be set when api.auth.type=login");
    }

    Map<String, Object> payload = new HashMap<>();
    payload.put(FrameworkConfig.getApiLoginUsernameField(), username);
    payload.put(FrameworkConfig.getApiLoginPasswordField(), password);

    Response response =
        given()
            .baseUri(FrameworkConfig.getApiBaseUrl())
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(payload)
            .when()
            .post(FrameworkConfig.getApiLoginPath());

    int statusCode = response.statusCode();
    if (statusCode != 200 && statusCode != 201) {
      throw new IllegalStateException(
          "Login request failed. Expected 200/201 but was "
              + statusCode
              + ". Response body: "
              + response.asString());
    }

    String token = response.jsonPath().getString(FrameworkConfig.getApiLoginTokenJsonPath());
    if (token == null || token.isBlank()) {
      throw new IllegalStateException(
          "Token could not be extracted from login response using json path: "
              + FrameworkConfig.getApiLoginTokenJsonPath());
    }

    return token;
  }
}
