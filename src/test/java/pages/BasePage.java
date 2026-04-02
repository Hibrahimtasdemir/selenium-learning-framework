package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.DemoUtils;
import utils.WaitUtils;

import java.util.List;

public abstract class BasePage {

    protected WebDriver driver;
    protected WaitUtils waitUtils;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    protected WebElement find(By locator) {
        return waitUtils.waitForVisible(locator);
    }

    protected List<WebElement> findAll(By locator) {
        return waitUtils.waitForAllVisible(locator);
    }

    protected void openUrl(String url) {
        driver.get(url);
        pauseForDemo();
    }

    protected void navigateTo(String url) {
        driver.navigate().to(url);
        pauseForDemo();
    }

    protected void navigateBack() {
        driver.navigate().back();
        pauseForDemo();
    }

    protected void navigateForward() {
        driver.navigate().forward();
        pauseForDemo();
    }

    protected void refreshPage() {
        driver.navigate().refresh();
        pauseForDemo();
    }

    protected void click(By locator) {
        waitUtils.waitForClickable(locator).click();
        pauseForDemo();
    }

    protected void click(WebElement element) {
        element.click();
        pauseForDemo();
    }

    protected void type(By locator, String text) {
        WebElement element = waitUtils.waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
        pauseForDemo();
    }

    protected String getText(By locator) {
        return waitUtils.waitForVisible(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        return waitUtils.waitForVisible(locator).isDisplayed();
    }

    protected void pauseForDemo() {
        DemoUtils.pause();
    }
}
