package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NestedFramesPage extends BasePage {

    private final By body = By.tagName("body");

    public NestedFramesPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPath("/nested_frames");
    }

    public void switchToTopFrame() {
        driver.switchTo().frame("frame-top");
        pauseForDemo();
    }

    public void switchToLeftFrame() {
        driver.switchTo().frame("frame-left");
        pauseForDemo();
    }

    public void switchToMiddleFrame() {
        driver.switchTo().frame("frame-middle");
        pauseForDemo();
    }

    public void switchToRightFrame() {
        driver.switchTo().frame("frame-right");
        pauseForDemo();
    }

    public void switchToBottomFrame() {
        driver.switchTo().frame("frame-bottom");
        pauseForDemo();
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
        pauseForDemo();
    }

    public String getFrameText() {
        return getText(body);
    }
}
