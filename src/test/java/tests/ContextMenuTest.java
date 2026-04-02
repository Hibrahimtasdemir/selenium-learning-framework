package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ContextMenuPage;

public class ContextMenuTest extends BaseTest {

  @Test(groups = {"regression"})
  public void shouldShowAlertAfterRightClick() {
    ContextMenuPage contextMenuPage = new ContextMenuPage(driver);

    contextMenuPage.open();
    contextMenuPage.rightClickHotSpot();

    Assert.assertEquals(
        contextMenuPage.getAlertText(),
        "You selected a context menu",
        "Unexpected context menu alert text.");

    contextMenuPage.acceptAlert();
  }
}
