package pages;

import org.openqa.selenium.WebDriver;

public class NavigationPage extends BasePage {

    private static final String HOME_URL = "https://the-internet.herokuapp.com/";
    private static final String LOGIN_URL = "https://the-internet.herokuapp.com/login";

    public NavigationPage(WebDriver driver) {
        super(driver);
    }

    public void openHomePage() {
        openUrl(HOME_URL);
        waitForTitle("The Internet");
    }

    public void goToLoginPage() {
        navigateTo(LOGIN_URL);
        waitForUrlContains("/login");
    }

    public void goBackToHomePage() {
        navigateBack();
        waitForTitle("The Internet");
    }

    public void goForwardToLoginPage() {
        navigateForward();
        waitForUrlContains("/login");
    }

    public void refreshLoginPage() {
        refreshPage();
        waitForUrlContains("/login");
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    private void waitForTitle(String title) {
        waitUtils.waitForTitleIs(title);
    }

    private void waitForUrlContains(String text) {
        waitUtils.waitForUrlContains(text);
    }
}
