package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CheckboxesPage;

public class CheckboxListTest extends BaseTest {

    @Test(groups = {"regression"})
    public void shouldSelectAllCheckboxes() {
        CheckboxesPage checkboxesPage = new CheckboxesPage(driver);

        checkboxesPage.open();

        Assert.assertEquals(checkboxesPage.getCheckboxCount(), 2, "Checkbox count is not correct.");

        checkboxesPage.selectAllCheckboxes();

        Assert.assertEquals(checkboxesPage.getSelectedCheckboxCount(), 2,
                "Not all checkboxes are selected.");

        Assert.assertTrue(checkboxesPage.isCheckboxSelected(1), "First checkbox should be selected.");
        Assert.assertTrue(checkboxesPage.isCheckboxSelected(2), "Second checkbox should be selected.");
    }
}
