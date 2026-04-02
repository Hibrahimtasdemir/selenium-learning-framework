package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class HoverPage extends BasePage {

    private final By userImages = By.cssSelector(".figure img");

    public HoverPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openUrl("https://the-internet.herokuapp.com/hovers");
    }

    public void hoverOverUser(int userIndex) {
        WebElement image = getUserImage(userIndex);
        new Actions(driver).moveToElement(image).perform();
        pauseForDemo();
    }

    public boolean isUserCaptionDisplayed(int userIndex) {
        return isDisplayed(getCaptionLocator(userIndex));
    }

    public String getUserCaptionText(int userIndex) {
        return getText(getCaptionLocator(userIndex));
    }

    public String getUserProfileLinkText(int userIndex) {
        return getText(getProfileLinkLocator(userIndex));
    }

    private WebElement getUserImage(int userIndex) {
        List<WebElement> images = findAll(userImages);

        if (userIndex < 1 || userIndex > images.size()) {
            throw new IllegalArgumentException("Invalid user index: " + userIndex);
        }

        return images.get(userIndex - 1);
    }

    private By getCaptionLocator(int userIndex) {
        return By.cssSelector(".figure:nth-of-type(" + userIndex + ") .figcaption h5");
    }

    private By getProfileLinkLocator(int userIndex) {
        return By.cssSelector(".figure:nth-of-type(" + userIndex + ") .figcaption a");
    }
}
