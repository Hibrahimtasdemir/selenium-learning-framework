package tests.ui;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InputPage;

public class InputTest extends BaseTest {

  @Severity(SeverityLevel.NORMAL)

  @Test(groups = {"regression"})
  public void shouldTypeIntoInputField() {
    InputPage inputPage = new InputPage(driver);

    inputPage.open();
    inputPage.typeNumber("12345");

    Assert.assertEquals(inputPage.getInputValue(), "12345");
  }
}
