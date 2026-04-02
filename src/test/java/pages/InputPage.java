package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InputPage extends BasePage {

    private final By numberInput = By.cssSelector("input[type='number']");

    public InputPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPath("/inputs");
    }

    public void typeNumber(String value) {
        type(numberInput, value);
    }

    public String getInputValue() {
        return find(numberInput).getAttribute("value");
    }
}
