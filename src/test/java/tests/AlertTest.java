package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AlertsPage;

public class AlertTest extends BaseTest {

  @Test(groups = {"regression"})
  public void shouldAcceptJsAlert() {
    AlertsPage alertsPage = new AlertsPage(driver);

    alertsPage.open();
    alertsPage.acceptJsAlert();

    Assert.assertTrue(alertsPage.isResultMessageDisplayed("You successfully clicked an alert"));
    Assert.assertEquals(alertsPage.getResultMessage(), "You successfully clicked an alert");
  }

  @Test(groups = {"regression"})
  public void shouldDismissJsConfirm() {
    AlertsPage alertsPage = new AlertsPage(driver);

    alertsPage.open();
    alertsPage.dismissJsConfirm();

    Assert.assertTrue(alertsPage.isResultMessageDisplayed("You clicked: Cancel"));
    Assert.assertEquals(alertsPage.getResultMessage(), "You clicked: Cancel");
  }

  @Test(groups = {"regression"})
  public void shouldSendTextToJsPrompt() {
    AlertsPage alertsPage = new AlertsPage(driver);

    alertsPage.open();
    alertsPage.sendTextToPrompt("Halil");

    Assert.assertTrue(alertsPage.isResultMessageDisplayed("You entered: Halil"));
    Assert.assertEquals(alertsPage.getResultMessage(), "You entered: Halil");
  }
}
