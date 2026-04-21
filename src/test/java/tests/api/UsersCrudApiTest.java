package tests.api;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import base.BaseApiTest;
import config.FrameworkConfig;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;
import testdata.ApiContractLoader;

public class UsersCrudApiTest extends BaseApiTest {

  @Test(groups = {"api", "api-regression"})
  public void shouldCreateUser() {
    Map<String, Object> requestBody =
        ApiContractLoader.loadJsonObject(FrameworkConfig.getApiUsersCreatePayloadPath());

    ValidatableResponse validatableResponse =
        apiRequest()
            .body(requestBody)
            .when()
            .post(FrameworkConfig.getApiUsersPath())
            .then()
            .statusCode(anyOf(is(200), is(201)))
            .body(FrameworkConfig.getApiUsersIdField(), notNullValue())
            .body(matchesJsonSchemaInClasspath(FrameworkConfig.getApiUserResponseSchemaPath()));

    assertEchoFields(validatableResponse, requestBody, FrameworkConfig.getApiUsersEchoFields());
  }

  @Test(groups = {"api", "api-regression"})
  public void shouldUpdateUser() {
    Map<String, Object> requestBody =
        ApiContractLoader.loadJsonObject(FrameworkConfig.getApiUsersUpdatePayloadPath());
    int sampleUserId = FrameworkConfig.getApiUsersSampleId();

    ValidatableResponse validatableResponse =
        apiRequest()
            .body(requestBody)
            .when()
            .put(FrameworkConfig.getApiUsersPath() + "/{id}", sampleUserId)
            .then()
            .statusCode(anyOf(is(200), is(201)))
            .body(FrameworkConfig.getApiUsersIdField(), notNullValue())
            .body(matchesJsonSchemaInClasspath(FrameworkConfig.getApiUserResponseSchemaPath()));

    assertEchoFields(validatableResponse, requestBody, FrameworkConfig.getApiUsersEchoFields());
  }

  @Test(groups = {"api", "api-regression"})
  public void shouldDeleteUser() {
    int sampleUserId = FrameworkConfig.getApiUsersSampleId();
    Response response =
        apiRequest().when().delete(FrameworkConfig.getApiUsersPath() + "/{id}", sampleUserId);

    Assert.assertTrue(
        response.getStatusCode() == 200 || response.getStatusCode() == 204,
        "Expected status code 200 or 204 but was " + response.getStatusCode());

    if (response.getStatusCode() != 204) {
      response.then().body(matchesJsonSchemaInClasspath(FrameworkConfig.getApiDeleteResponseSchemaPath()));
    }
  }

  private void assertEchoFields(
      ValidatableResponse response, Map<String, Object> requestBody, List<String> echoFields) {
    for (String field : echoFields) {
      Object expectedValue = requestBody.get(field);
      if (expectedValue != null) {
        response.body(field, is(expectedValue));
      }
    }
  }
}
