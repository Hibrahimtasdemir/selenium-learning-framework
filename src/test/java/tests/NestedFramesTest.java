package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.NestedFramesPage;

public class NestedFramesTest extends BaseTest {

  @Test(groups = {"regression"})
  public void shouldReadTextsFromNestedFrames() {
    NestedFramesPage nestedFramesPage = new NestedFramesPage(driver);

    nestedFramesPage.open();

    nestedFramesPage.switchToTopFrame();
    nestedFramesPage.switchToLeftFrame();
    Assert.assertEquals(nestedFramesPage.getFrameText(), "LEFT", "Unexpected text in left frame.");

    nestedFramesPage.switchToDefaultContent();
    nestedFramesPage.switchToTopFrame();
    nestedFramesPage.switchToMiddleFrame();
    Assert.assertEquals(
        nestedFramesPage.getFrameText(), "MIDDLE", "Unexpected text in middle frame.");

    nestedFramesPage.switchToDefaultContent();
    nestedFramesPage.switchToTopFrame();
    nestedFramesPage.switchToRightFrame();
    Assert.assertEquals(
        nestedFramesPage.getFrameText(), "RIGHT", "Unexpected text in right frame.");

    nestedFramesPage.switchToDefaultContent();
    nestedFramesPage.switchToBottomFrame();
    Assert.assertEquals(
        nestedFramesPage.getFrameText(), "BOTTOM", "Unexpected text in bottom frame.");
  }
}
