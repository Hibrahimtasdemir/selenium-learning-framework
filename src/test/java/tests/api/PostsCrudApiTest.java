package tests.api;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

import base.BaseApiTest;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PostsCrudApiTest extends BaseApiTest {

  @Severity(SeverityLevel.NORMAL)
  @Test(groups = {"api", "api-regression"})
  public void shouldCreatePost() {
    Map<String, Object> requestBody =
        Map.of(
            "title", "Created by Rest-Assured",
            "body", "API regression create scenario",
            "userId", 11);

    apiRequest()
        .body(requestBody)
        .when()
        .post("/posts")
        .then()
        .statusCode(201)
        .body("id", greaterThan(0))
        .body("title", equalTo("Created by Rest-Assured"))
        .body("body", equalTo("API regression create scenario"))
        .body("userId", equalTo(11))
        .body(matchesJsonSchemaInClasspath("schemas/posts/post-response.schema.json"));
  }

  @Severity(SeverityLevel.NORMAL)
  @Test(groups = {"api", "api-regression"})
  public void shouldUpdatePost() {
    Map<String, Object> requestBody =
        Map.of(
            "id", 1,
            "title", "Updated by Rest-Assured",
            "body", "API regression update scenario",
            "userId", 11);

    apiRequest()
        .body(requestBody)
        .when()
        .put("/posts/{id}", 1)
        .then()
        .statusCode(200)
        .body("id", equalTo(1))
        .body("title", equalTo("Updated by Rest-Assured"))
        .body("body", equalTo("API regression update scenario"))
        .body("userId", equalTo(11))
        .body(matchesJsonSchemaInClasspath("schemas/posts/post-response.schema.json"));
  }

  @Severity(SeverityLevel.NORMAL)
  @Test(groups = {"api", "api-regression"})
  public void shouldDeletePost() {
    Response response = apiRequest().when().delete("/posts/{id}", 1);

    Assert.assertTrue(
        response.getStatusCode() == 200 || response.getStatusCode() == 204,
        "Expected status code 200 or 204 but was " + response.getStatusCode());

    if (response.getStatusCode() != 204) {
      response
          .then()
          .body(matchesJsonSchemaInClasspath("schemas/posts/delete-response.schema.json"));
    }
  }
}
