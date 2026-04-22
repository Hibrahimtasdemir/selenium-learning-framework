package tests.ui;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DragAndDropPage;

public class DragAndDropTest extends BaseTest {

  @Severity(SeverityLevel.MINOR)

  @Test(groups = {"regression"})
  public void shouldSwapColumnsAfterDragAndDrop() {
    DragAndDropPage dragAndDropPage = new DragAndDropPage(driver);

    dragAndDropPage.open();
    dragAndDropPage.dragColumnAToColumnB();

    Assert.assertEquals(
        dragAndDropPage.getColumnAText(),
        "B",
        "Column A header should become B after drag and drop.");
    Assert.assertEquals(
        dragAndDropPage.getColumnBText(),
        "A",
        "Column B header should become A after drag and drop.");
  }
}
