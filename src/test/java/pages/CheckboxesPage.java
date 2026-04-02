package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckboxesPage extends BasePage {

    private final By checkboxes = By.cssSelector("input[type='checkbox']");

    public CheckboxesPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openUrl("https://the-internet.herokuapp.com/checkboxes");
    }

    public int getCheckboxCount() {
        return findAll(checkboxes).size();
    }

    public void selectCheckbox(int index) {
        WebElement checkbox = getCheckboxByIndex(index);

        if (!checkbox.isSelected()) {
            click(checkbox);
        }
    }

    public void unselectCheckbox(int index) {
        WebElement checkbox = getCheckboxByIndex(index);

        if (checkbox.isSelected()) {
            click(checkbox);
        }
    }

    public boolean isCheckboxSelected(int index) {
        return getCheckboxByIndex(index).isSelected();
    }

    public boolean isCheckboxDisplayed(int index) {
        return getCheckboxByIndex(index).isDisplayed();
    }

    public boolean isCheckboxEnabled(int index) {
        return getCheckboxByIndex(index).isEnabled();
    }

    public void selectAllCheckboxes() {
        List<WebElement> allCheckboxes = findAll(checkboxes);

        for (WebElement checkbox : allCheckboxes) {
            if (!checkbox.isSelected()) {
                click(checkbox);
            }
        }
    }

    public int getSelectedCheckboxCount() {
        int count = 0;
        List<WebElement> allCheckboxes = findAll(checkboxes);

        for (WebElement checkbox : allCheckboxes) {
            if (checkbox.isSelected()) {
                count++;
            }
        }

        return count;
    }

    private WebElement getCheckboxByIndex(int index) {
        List<WebElement> allCheckboxes = findAll(checkboxes);

        if (index < 1 || index > allCheckboxes.size()) {
            throw new IllegalArgumentException("Invalid checkbox index: " + index);
        }

        return allCheckboxes.get(index - 1);
    }
}
