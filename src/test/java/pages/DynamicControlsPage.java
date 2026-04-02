package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DynamicControlsPage extends BasePage {

    private final By checkbox = By.cssSelector("#checkbox input[type='checkbox']");
    private final By removeAddButton = By.cssSelector("#checkbox-example button");
    private final By inputField = By.cssSelector("#input-example input");
    private final By enableDisableButton = By.cssSelector("#input-example button");
    private final By message = By.id("message");

    public DynamicControlsPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPath("/dynamic_controls");
    }

    public void clickRemoveButton() {
        click(removeAddButton);
    }

    public void clickEnableButton() {
        click(enableDisableButton);
    }

    public void waitForCheckboxToDisappear() {
        waitUtils.waitForInvisibility(checkbox);
    }

    public void waitForInputToBeEnabled() {
        waitUtils.waitForClickable(inputField);
    }

    public boolean isCheckboxDisplayed() {
        return !driver.findElements(checkbox).isEmpty() && driver.findElement(checkbox).isDisplayed();
    }

    public boolean isInputEnabled() {
        return find(inputField).isEnabled();
    }

    public String getMessageText() {
        return getText(message);
    }
}
