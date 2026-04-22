package tests.ui;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CheckboxesPage;
import testdata.TestDataProviders;

public class CheckboxTest extends BaseTest {

  @Severity(SeverityLevel.MINOR)

  @Test(
      groups = {"smoke", "regression"},
      dataProvider = "checkboxIndexData",
      dataProviderClass = TestDataProviders.class)
  public void shouldSelectCheckbox(int checkboxIndex) {
    CheckboxesPage checkboxesPage = new CheckboxesPage(driver);

    checkboxesPage.open();
    checkboxesPage.selectCheckbox(checkboxIndex);

    Assert.assertTrue(
        checkboxesPage.isCheckboxSelected(checkboxIndex), "Checkbox is not selected.");
  }
}
