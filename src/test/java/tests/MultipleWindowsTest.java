package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MultipleWindowsPage;

public class MultipleWindowsTest extends BaseTest {

    @Test(groups = {"smoke", "regression"})
    public void shouldSwitchToNewWindow() {
        MultipleWindowsPage multipleWindowsPage = new MultipleWindowsPage(driver);

        multipleWindowsPage.open();
        String originalWindowHandle = multipleWindowsPage.getCurrentWindowHandle();

        multipleWindowsPage.clickLinkThatOpensNewWindow();
        multipleWindowsPage.switchToNewWindowFrom(originalWindowHandle);

        Assert.assertEquals(multipleWindowsPage.getNewWindowHeading(), "New Window");

        multipleWindowsPage.switchBackToWindow(originalWindowHandle);
    }
}
