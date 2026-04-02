package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class KeyPressesPage extends BasePage {

    private final By targetInput = By.id("target");
    private final By resultText = By.id("result");

    public KeyPressesPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openUrl("https://the-internet.herokuapp.com/key_presses");
    }

    public void pressKey(CharSequence key) {
        find(targetInput).sendKeys(key);
        pauseForDemo();
    }

    public String getResultText() {
        return getText(resultText);
    }

    public boolean isResultDisplayed(String expectedText) {
        return waitUtils.waitForTextContains(resultText, expectedText);
    }
}
