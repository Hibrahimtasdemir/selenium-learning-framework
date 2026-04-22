package tests.ui;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ChallengingDomPage;

public class ChallengingDomTest extends BaseTest {

  @Severity(SeverityLevel.MINOR)

  @Test(groups = {"regression"})
  public void shouldUseStableLocatorsOnChallengingDomPage() {
    ChallengingDomPage challengingDomPage = new ChallengingDomPage(driver);

    challengingDomPage.open();

    Assert.assertTrue(
        challengingDomPage.isValidDynamicButtonText(challengingDomPage.getPrimaryButtonText()),
        "Primary button text is not one of the expected values.");
    Assert.assertTrue(
        challengingDomPage.isValidDynamicButtonText(challengingDomPage.getAlertButtonText()),
        "Alert button text is not one of the expected values.");
    Assert.assertTrue(
        challengingDomPage.isValidDynamicButtonText(challengingDomPage.getSuccessButtonText()),
        "Success button text is not one of the expected values.");

    Assert.assertEquals(challengingDomPage.getRowCount(), 10, "Unexpected table row count.");
    Assert.assertEquals(
        challengingDomPage.getCellText(1, 1), "Iuvaret0", "Unexpected text in first table cell.");
    Assert.assertEquals(
        challengingDomPage.getCellText(10, 6),
        "Phaedrum9",
        "Unexpected text in last row sixth column.");
    Assert.assertTrue(
        challengingDomPage.isEditLinkDisplayed(1), "Edit link should be visible in first row.");
    Assert.assertTrue(
        challengingDomPage.isDeleteLinkDisplayed(1), "Delete link should be visible in first row.");
  }
}
