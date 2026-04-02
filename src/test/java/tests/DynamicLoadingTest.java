package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DynamicLoadingPage;

public class DynamicLoadingTest extends BaseTest {

    @Test
    public void shouldWaitForHiddenElementToAppear() {
        DynamicLoadingPage dynamicLoadingPage = new DynamicLoadingPage(driver);

        dynamicLoadingPage.openExampleTwo();
        dynamicLoadingPage.clickStart();
        dynamicLoadingPage.waitForLoadingToFinish();

        Assert.assertTrue(dynamicLoadingPage.isFinishTextDisplayed(),
                "Finish text should be visible after loading completes.");
        Assert.assertEquals(dynamicLoadingPage.getFinishText(), "Hello World!",
                "Unexpected finish text.");
    }
}
