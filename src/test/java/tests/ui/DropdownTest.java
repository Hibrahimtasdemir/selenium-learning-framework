package tests.ui;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DropdownPage;
import testdata.TestDataProviders;

public class DropdownTest extends BaseTest {

  @Severity(SeverityLevel.MINOR)

  @Test(
      groups = {"smoke", "regression"},
      dataProvider = "dropdownSelectionData",
      dataProviderClass = TestDataProviders.class)
  public void shouldSelectOption(
      String selectionType, String selectionValue, String expectedOption) {
    DropdownPage dropdownPage = new DropdownPage(driver);

    dropdownPage.open();

    if (selectionType.equals("visibleText")) {
      dropdownPage.selectByVisibleText(selectionValue);
    } else {
      dropdownPage.selectByValue(selectionValue);
    }

    Assert.assertEquals(
        dropdownPage.getSelectedOptionText(), expectedOption, "Expected option was not selected.");
  }
}
