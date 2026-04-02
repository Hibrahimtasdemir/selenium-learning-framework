package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class DropdownPage extends BasePage {

    private final By dropdown = By.id("dropdown");

    public DropdownPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openUrl("https://the-internet.herokuapp.com/dropdown");
    }

    public void selectByVisibleText(String text) {
        Select select = new Select(find(dropdown));
        select.selectByVisibleText(text);
    }

    public void selectByValue(String value) {
        Select select = new Select(find(dropdown));
        select.selectByValue(value);
    }

    public String getSelectedOptionText() {
        Select select = new Select(find(dropdown));
        return select.getFirstSelectedOption().getText();
    }
}
