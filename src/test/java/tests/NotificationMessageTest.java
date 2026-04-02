package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.NotificationMessagePage;

public class NotificationMessageTest extends BaseTest {

  @Test(groups = {"regression"})
  public void shouldDisplayOneOfExpectedNotificationMessages() {
    NotificationMessagePage notificationMessagePage = new NotificationMessagePage(driver);

    notificationMessagePage.open();
    notificationMessagePage.clickHere();

    Assert.assertTrue(
        notificationMessagePage.isNotificationOneOf(
            "Action successful", "Action unsuccesful, please try again", "Action unsuccesful"),
        "Unexpected notification message: " + notificationMessagePage.getNotificationMessage());
  }
}
