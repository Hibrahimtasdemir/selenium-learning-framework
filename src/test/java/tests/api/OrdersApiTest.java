package tests.api;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import base.BaseApiTest;
import config.FrameworkConfig;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;
import testdata.ApiContractLoader;

public class OrdersApiTest extends BaseApiTest {

  @Severity(SeverityLevel.CRITICAL)
  @Test(groups = {"api", "api-smoke"})
  public void shouldGetOrderById() {
    int sampleOrderId = FrameworkConfig.getApiOrdersSampleId();

    Response response =
        apiRequest().when().get(FrameworkConfig.getApiOrdersPath() + "/{id}", sampleOrderId);

    response
        .then()
        .statusCode(200)
        .body(matchesJsonSchemaInClasspath(FrameworkConfig.getApiOrderResponseSchemaPath()));

    Object responseOrderId = response.path(FrameworkConfig.getApiOrdersIdField());
    Assert.assertEquals(String.valueOf(responseOrderId), String.valueOf(sampleOrderId));
  }

  @Severity(SeverityLevel.NORMAL)
  @Test(groups = {"api", "api-regression"})
  public void shouldCreateOrder() {
    Map<String, Object> requestBody =
        ApiContractLoader.loadJsonObject(FrameworkConfig.getApiOrdersCreatePayloadPath());

    ValidatableResponse validatableResponse =
        apiRequest()
            .body(requestBody)
            .when()
            .post(FrameworkConfig.getApiOrdersPath())
            .then()
            .statusCode(anyOf200Or201())
            .body(FrameworkConfig.getApiOrdersIdField(), notNullValue())
            .body(matchesJsonSchemaInClasspath(FrameworkConfig.getApiOrderResponseSchemaPath()));

    assertEchoFields(validatableResponse, requestBody, FrameworkConfig.getApiOrdersEchoFields());
  }

  @Severity(SeverityLevel.NORMAL)
  @Test(groups = {"api", "api-regression"})
  public void shouldUpdateOrder() {
    Map<String, Object> requestBody =
        ApiContractLoader.loadJsonObject(FrameworkConfig.getApiOrdersUpdatePayloadPath());
    int sampleOrderId = FrameworkConfig.getApiOrdersSampleId();

    ValidatableResponse validatableResponse =
        apiRequest()
            .body(requestBody)
            .when()
            .put(FrameworkConfig.getApiOrdersPath() + "/{id}", sampleOrderId)
            .then()
            .statusCode(anyOf200Or201())
            .body(FrameworkConfig.getApiOrdersIdField(), notNullValue())
            .body(matchesJsonSchemaInClasspath(FrameworkConfig.getApiOrderResponseSchemaPath()));

    assertEchoFields(validatableResponse, requestBody, FrameworkConfig.getApiOrdersEchoFields());
  }

  @Severity(SeverityLevel.NORMAL)
  @Test(groups = {"api", "api-regression"})
  public void shouldDeleteOrder() {
    int sampleOrderId = FrameworkConfig.getApiOrdersSampleId();
    Response response =
        apiRequest().when().delete(FrameworkConfig.getApiOrdersPath() + "/{id}", sampleOrderId);
    int statusCode = response.statusCode();

    org.testng.Assert.assertTrue(
        statusCode == 200 || statusCode == 204,
        "Expected status code 200 or 204 but was " + statusCode);

    if (statusCode != 204) {
      response.then().body(matchesJsonSchemaInClasspath(FrameworkConfig.getApiDeleteResponseSchemaPath()));
    }
  }

  private static org.hamcrest.Matcher<Integer> anyOf200Or201() {
    return anyOf(is(200), is(201));
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
