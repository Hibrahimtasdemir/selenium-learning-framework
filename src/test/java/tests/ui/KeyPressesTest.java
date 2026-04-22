package tests.ui;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.KeyPressesPage;
import testdata.TestDataProviders;

public class KeyPressesTest extends BaseTest {

  @Severity(SeverityLevel.NORMAL)

  @Test(
      groups = {"regression"},
      dataProvider = "keyPressData",
      dataProviderClass = TestDataProviders.class)
  public void shouldDisplayPressedKey(CharSequence key, String expectedText) {
    KeyPressesPage keyPressesPage = new KeyPressesPage(driver);

    keyPressesPage.open();
    keyPressesPage.pressKey(key);

    Assert.assertTrue(
        keyPressesPage.isResultDisplayed(expectedText), "Expected key result was not displayed.");
    Assert.assertEquals(
        keyPressesPage.getResultText(), expectedText, "Unexpected key result text.");
  }
}
