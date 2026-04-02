package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CheckboxesPage;
import testdata.TestDataProviders;

public class CheckboxTest extends BaseTest {

    @Test(dataProvider = "checkboxIndexData", dataProviderClass = TestDataProviders.class)
    public void shouldSelectCheckbox(int checkboxIndex) {
        CheckboxesPage checkboxesPage = new CheckboxesPage(driver);

        checkboxesPage.open();
        checkboxesPage.selectCheckbox(checkboxIndex);

        Assert.assertTrue(checkboxesPage.isCheckboxSelected(checkboxIndex), "Checkbox is not selected.");
    }
}
