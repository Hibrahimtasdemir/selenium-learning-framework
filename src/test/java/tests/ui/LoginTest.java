package tests.ui;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

  @Severity(SeverityLevel.CRITICAL)

  @Test(groups = {"smoke", "regression"})
  public void shouldLoginSuccessfully() {
    LoginPage loginPage = new LoginPage(driver);

    loginPage.open();
    loginPage.login("tomsmith", "SuperSecretPassword!");

    Assert.assertTrue(
        loginPage.isLoginSuccessful(), "Success message was not displayed correctly.");
  }
}
