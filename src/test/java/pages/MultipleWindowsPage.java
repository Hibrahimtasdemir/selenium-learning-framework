package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public class MultipleWindowsPage extends BasePage {

    private final By clickHereLink = By.linkText("Click Here");
    private final By newWindowHeading = By.tagName("h3");

    public MultipleWindowsPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openUrl("https://the-internet.herokuapp.com/windows");
    }

    public String getCurrentWindowHandle() {
        return driver.getWindowHandle();
    }

    public void clickLinkThatOpensNewWindow() {
        click(clickHereLink);
    }

    public void switchToNewWindowFrom(String originalWindowHandle) {
        waitUtils.waitForNumberOfWindowsToBe(2);
        Set<String> windowHandles = driver.getWindowHandles();

        for (String handle : windowHandles) {
            if (!handle.equals(originalWindowHandle)) {
                driver.switchTo().window(handle);
                pauseForDemo();
                return;
            }
        }

        throw new IllegalStateException("New window was not found.");
    }

    public void switchBackToWindow(String windowHandle) {
        driver.switchTo().window(windowHandle);
        pauseForDemo();
    }

    public String getNewWindowHeading() {
        return getText(newWindowHeading);
    }
}
