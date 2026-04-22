package tests.api;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;

import base.BaseApiTest;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;

public class PostsApiTest extends BaseApiTest {

  @Severity(SeverityLevel.CRITICAL)
  @Test(groups = {"api", "api-smoke"})
  public void shouldGetPostById() {
    apiRequest()
        .when()
        .get("/posts/{id}", 1)
        .then()
        .statusCode(200)
        .body("id", equalTo(1))
        .body("userId", greaterThan(0))
        .body("title", not(blankOrNullString()))
        .body(matchesJsonSchemaInClasspath("schemas/posts/post-response.schema.json"));
  }
}
