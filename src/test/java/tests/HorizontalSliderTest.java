package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HorizontalSliderPage;

public class HorizontalSliderTest extends BaseTest {

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
