package tests.ui;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HorizontalSliderPage;

public class HorizontalSliderTest extends BaseTest {

  @Severity(SeverityLevel.MINOR)

  @Test(groups = {"regression"})
  public void shouldMoveSliderToExpectedValue() {
    HorizontalSliderPage horizontalSliderPage = new HorizontalSliderPage(driver);

    horizontalSliderPage.open();
    horizontalSliderPage.moveSliderRight(3);

    Assert.assertEquals(
        horizontalSliderPage.getSliderValue(),
        "1.5",
        "Slider value should be 1.5 after moving right three times.");
  }
}
