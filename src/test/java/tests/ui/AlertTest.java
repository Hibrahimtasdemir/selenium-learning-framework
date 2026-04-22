package tests.ui;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AlertsPage;

public class AlertTest extends BaseTest {

  @Severity(SeverityLevel.MINOR)

  @Test(groups = {"regression"})
  public void shouldAcceptJsAlert() {
    AlertsPage alertsPage = new AlertsPage(driver);

    alertsPage.open();
    alertsPage.acceptJsAlert();

    Assert.assertTrue(alertsPage.isResultMessageDisplayed("You successfully clicked an alert"));
    Assert.assertEquals(alertsPage.getResultMessage(), "You successfully clicked an alert");
  }

  @Severity(SeverityLevel.MINOR)

  @Test(groups = {"regression"})
  public void shouldDismissJsConfirm() {
    AlertsPage alertsPage = new AlertsPage(driver);

    alertsPage.open();
    alertsPage.dismissJsConfirm();

    Assert.assertTrue(alertsPage.isResultMessageDisplayed("You clicked: Cancel"));
    Assert.assertEquals(alertsPage.getResultMessage(), "You clicked: Cancel");
  }

  @Severity(SeverityLevel.MINOR)

  @Test(groups = {"regression"})
  public void shouldSendTextToJsPrompt() {
    AlertsPage alertsPage = new AlertsPage(driver);

    alertsPage.open();
    alertsPage.sendTextToPrompt("Halil");

    Assert.assertTrue(alertsPage.isResultMessageDisplayed("You entered: Halil"));
    Assert.assertEquals(alertsPage.getResultMessage(), "You entered: Halil");
  }
}
