package tests.ui;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.TablePage;

public class TableTest extends BaseTest {

  @Severity(SeverityLevel.NORMAL)

  @Test(groups = {"regression"})
  public void shouldReadDataFromTable() {
    TablePage tablePage = new TablePage(driver);

    tablePage.open();

    Assert.assertEquals(tablePage.getRowCount(), 4, "Unexpected row count in table.");
    Assert.assertEquals(
        tablePage.getEmailByLastName("Doe"),
        "jdoe@hotmail.com",
        "Email for Doe row is not correct.");
    Assert.assertEquals(
        tablePage.getDueAmountByLastName("Smith"),
        "$50.00",
        "Due amount for Smith row is not correct.");
  }
}
