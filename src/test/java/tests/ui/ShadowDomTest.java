package tests.ui;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ShadowDomPage;

public class ShadowDomTest extends BaseTest {

  @Severity(SeverityLevel.MINOR)

  @Test(groups = {"regression"})
  public void shouldReadTextFromShadowDom() {
    ShadowDomPage shadowDomPage = new ShadowDomPage(driver);

    shadowDomPage.open();

    Assert.assertEquals(
        shadowDomPage.getParagraphHostCount(), 2, "Unexpected number of shadow hosts.");
    Assert.assertEquals(
        shadowDomPage.getShadowParagraphText(1),
        "Let's have some different text!",
        "Unexpected text in first shadow paragraph.");
    Assert.assertTrue(
        shadowDomPage.getShadowParagraphText(2).contains("In a list!"),
        "Second shadow paragraph should include slotted list text.");
  }
}
