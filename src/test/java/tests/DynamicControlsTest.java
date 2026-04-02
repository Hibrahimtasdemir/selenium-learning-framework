package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DynamicControlsPage;

public class DynamicControlsTest extends BaseTest {

  @Test(groups = {"regression"})
  public void shouldRemoveCheckboxAndEnableInput() {
    DynamicControlsPage dynamicControlsPage = new DynamicControlsPage(driver);

    dynamicControlsPage.open();

    Assert.assertTrue(
        dynamicControlsPage.isCheckboxDisplayed(), "Checkbox should be visible at the start.");
    dynamicControlsPage.clickRemoveButton();
    dynamicControlsPage.waitForCheckboxToDisappear();

    Assert.assertEquals(
        dynamicControlsPage.getMessageText(), "It's gone!", "Unexpected remove checkbox message.");
    Assert.assertFalse(
        dynamicControlsPage.isCheckboxDisplayed(),
        "Checkbox should not be visible after remove action.");

    Assert.assertFalse(
        dynamicControlsPage.isInputEnabled(), "Input should be disabled at the start.");
    dynamicControlsPage.clickEnableButton();
    dynamicControlsPage.waitForInputToBeEnabled();

    Assert.assertEquals(
        dynamicControlsPage.getMessageText(), "It's enabled!", "Unexpected enable input message.");
    Assert.assertTrue(
        dynamicControlsPage.isInputEnabled(), "Input should be enabled after clicking enable.");
  }
}
