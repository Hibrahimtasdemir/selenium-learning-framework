package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FramesPage extends BasePage {

    private final By iframe = By.id("mce_0_ifr");
    private final By editorBody = By.id("tinymce");

    public FramesPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPath("/iframe");
    }

    public void switchToEditorFrame() {
        waitUtils.waitForFrameAndSwitchToIt(iframe);
        pauseForDemo();
    }

    public void clearEditor() {
        WebElement editor = find(editorBody);
        ((JavascriptExecutor) driver).executeScript("arguments[0].textContent = '';", editor);
        pauseForDemo();
    }

    public void typeIntoEditor(String text) {
        WebElement editor = find(editorBody);
        ((JavascriptExecutor) driver).executeScript("arguments[0].textContent = arguments[1];", editor, text);
        pauseForDemo();
    }

    public String getEditorText() {
        return getText(editorBody);
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
        pauseForDemo();
    }
}
