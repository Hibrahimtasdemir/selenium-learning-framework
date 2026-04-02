package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.NavigationPage;

public class NavigationTest extends BaseTest {

    @Test
    public void shouldNavigateBetweenPages() {
        NavigationPage navigationPage = new NavigationPage(driver);

        navigationPage.openHomePage();
        String homePageTitle = navigationPage.getPageTitle();
        Assert.assertEquals(homePageTitle, "The Internet");

        navigationPage.goToLoginPage();
        String loginPageUrl = navigationPage.getCurrentUrl();
        Assert.assertTrue(loginPageUrl.contains("/login"));

        navigationPage.goBackToHomePage();
        Assert.assertEquals(navigationPage.getPageTitle(), "The Internet");

        navigationPage.goForwardToLoginPage();
        Assert.assertTrue(navigationPage.getCurrentUrl().contains("/login"));

        navigationPage.refreshLoginPage();
        Assert.assertTrue(navigationPage.getCurrentUrl().contains("/login"));
    }
}
