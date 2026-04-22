package tests.ui;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BrokenImagesPage;

public class BrokenImagesTest extends BaseTest {

  @Severity(SeverityLevel.MINOR)

  @Test(groups = {"regression"})
  public void shouldDetectBrokenImages() {
    BrokenImagesPage brokenImagesPage = new BrokenImagesPage(driver);

    brokenImagesPage.open();

    Assert.assertEquals(
        brokenImagesPage.getImageCount(), 3, "Unexpected image count on broken images page.");
    Assert.assertTrue(
        brokenImagesPage.hasBrokenImages(), "Expected at least one broken image on the page.");
    Assert.assertEquals(
        brokenImagesPage.getBrokenImageCount(), 2, "Unexpected broken image count.");
  }
}
