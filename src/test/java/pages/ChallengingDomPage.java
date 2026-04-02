package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public class ChallengingDomPage extends BasePage {

    private final By primaryButton = By.cssSelector(".button");
    private final By alertButton = By.cssSelector(".button.alert");
    private final By successButton = By.cssSelector(".button.success");
    private final By tableRows = By.cssSelector("table tbody tr");

    public ChallengingDomPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPath("/challenging_dom");
    }

    public String getPrimaryButtonText() {
        return getText(primaryButton);
    }

    public String getAlertButtonText() {
        return getText(alertButton);
    }

    public String getSuccessButtonText() {
        return getText(successButton);
    }

    public boolean isValidDynamicButtonText(String buttonText) {
        return Set.of("foo", "bar", "baz", "qux").contains(buttonText);
    }

    public int getRowCount() {
        return findAll(tableRows).size();
    }

    public String getCellText(int rowIndex, int columnIndex) {
        By cellLocator = By.cssSelector("table tbody tr:nth-of-type(" + rowIndex + ") td:nth-of-type(" + columnIndex + ")");
        return getText(cellLocator);
    }

    public boolean isEditLinkDisplayed(int rowIndex) {
        By editLink = By.cssSelector("table tbody tr:nth-of-type(" + rowIndex + ") a[href='#edit']");
        return isDisplayed(editLink);
    }

    public boolean isDeleteLinkDisplayed(int rowIndex) {
        By deleteLink = By.cssSelector("table tbody tr:nth-of-type(" + rowIndex + ") a[href='#delete']");
        return isDisplayed(deleteLink);
    }
}
