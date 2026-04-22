package tests.ui;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class FirstTest extends BaseTest {

  @Severity(SeverityLevel.MINOR)

  @Test(groups = {"smoke", "regression"})
  public void openWebsiteAndVerifyTitle() {
    HomePage homePage = new HomePage(driver);
    String expectedTitle = "The Internet";

    homePage.open();

    Assert.assertEquals(homePage.getPageTitle(), expectedTitle);
  }
}
