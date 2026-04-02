package pages;

import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private static final String HOME_URL = "https://the-internet.herokuapp.com/";
    private static final String HOME_TITLE = "The Internet";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openUrl(HOME_URL);
        waitUtils.waitForTitleIs(HOME_TITLE);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}
