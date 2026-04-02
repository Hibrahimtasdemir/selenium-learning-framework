package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HoverPage;

public class HoverTest extends BaseTest {

    @Test
    public void shouldDisplayCaptionWhenHoveringOverUser() {
        HoverPage hoverPage = new HoverPage(driver);

        hoverPage.open();
        hoverPage.hoverOverUser(1);

        Assert.assertTrue(hoverPage.isUserCaptionDisplayed(1), "User caption should be displayed.");
        Assert.assertEquals(hoverPage.getUserCaptionText(1), "name: user1",
                "Unexpected hover caption text.");
        Assert.assertEquals(hoverPage.getUserProfileLinkText(1), "View profile",
                "Unexpected profile link text.");
    }
}
