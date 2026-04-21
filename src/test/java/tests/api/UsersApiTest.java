package tests.api;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import base.BaseApiTest;
import config.FrameworkConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UsersApiTest extends BaseApiTest {

  @Test(groups = {"api", "api-smoke"})
  public void shouldGetUserById() {
    int sampleUserId = FrameworkConfig.getApiUsersSampleId();

    Response response =
        apiRequest().when().get(FrameworkConfig.getApiUsersPath() + "/{id}", sampleUserId);

    response
        .then()
        .statusCode(200)
        .body(matchesJsonSchemaInClasspath(FrameworkConfig.getApiUserResponseSchemaPath()));

    Object responseUserId = response.path(FrameworkConfig.getApiUsersIdField());
    Assert.assertEquals(String.valueOf(responseUserId), String.valueOf(sampleUserId));
  }
}
