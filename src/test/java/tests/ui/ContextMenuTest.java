package tests.ui;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ContextMenuPage;

public class ContextMenuTest extends BaseTest {

  @Severity(SeverityLevel.MINOR)

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
