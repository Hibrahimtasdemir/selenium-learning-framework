package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(groups = {"smoke", "regression"})
    public void shouldLoginSuccessfully() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.open();
        loginPage.login("tomsmith", "SuperSecretPassword!");

        Assert.assertTrue(loginPage.isLoginSuccessful(),
                "Success message was not displayed correctly.");
    }
}
