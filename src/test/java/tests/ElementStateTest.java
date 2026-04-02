package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CheckboxesPage;

public class ElementStateTest extends BaseTest {

    @Test
    public void shouldCheckElementStates() {
        CheckboxesPage checkboxesPage = new CheckboxesPage(driver);

        checkboxesPage.open();

        Assert.assertTrue(checkboxesPage.isCheckboxDisplayed(1), "First checkbox is not displayed.");
        Assert.assertTrue(checkboxesPage.isCheckboxEnabled(1), "First checkbox is not enabled.");

        checkboxesPage.selectCheckbox(1);

        Assert.assertTrue(checkboxesPage.isCheckboxSelected(1), "First checkbox should be selected.");
        Assert.assertTrue(checkboxesPage.isCheckboxSelected(2), "Second checkbox should already be selected.");
    }
}
