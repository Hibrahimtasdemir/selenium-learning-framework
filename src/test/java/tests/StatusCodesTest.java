package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.StatusCodesPage;

public class StatusCodesTest extends BaseTest {

  @DataProvider(name = "statusCodeData")
  public Object[][] statusCodeData() {
    return new Object[][] {
      {200, "This page returned a 200 status code."},
      {301, "This page returned a 301 status code."},
      {404, "This page returned a 404 status code."},
      {500, "This page returned a 500 status code."}
    };
  }

  @Test(
      groups = {"regression"},
      dataProvider = "statusCodeData")
  public void shouldOpenStatusCodePage(int statusCode, String expectedMessage) {
    StatusCodesPage statusCodesPage = new StatusCodesPage(driver);

    statusCodesPage.open();
    statusCodesPage.clickStatusCodeLink(statusCode);

    Assert.assertTrue(
        statusCodesPage.getStatusMessage().contains(expectedMessage),
        "Unexpected status code message.");
  }
}
