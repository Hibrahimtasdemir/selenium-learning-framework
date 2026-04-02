package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;

public class NegativeLoginTest extends BaseTest {

  @DataProvider(name = "invalidLoginData")
  public Object[][] invalidLoginData() {
    return new Object[][] {
      {"wrongUser", "SuperSecretPassword!", "Your username is invalid!"},
      {"tomsmith", "wrongPassword", "Your password is invalid!"},
      {"", "", "Your username is invalid!"}
    };
  }

  @Test(
      groups = {"regression"},
      dataProvider = "invalidLoginData")
  public void shouldShowErrorMessageForInvalidLogin(
      String username, String password, String expectedMessage) {
    LoginPage loginPage = new LoginPage(driver);

    loginPage.open();
    loginPage.login(username, password);

    Assert.assertTrue(
        loginPage.getFlashMessage().contains(expectedMessage),
        "Expected error message was not displayed.");
  }
}
