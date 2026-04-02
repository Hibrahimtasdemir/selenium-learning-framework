package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DynamicLoadingPage extends BasePage {

    private final By startButton = By.cssSelector("#start button");
    private final By loadingIndicator = By.id("loading");
    private final By finishText = By.id("finish");

    public DynamicLoadingPage(WebDriver driver) {
        super(driver);
    }

    public void openExampleTwo() {
        openUrl("https://the-internet.herokuapp.com/dynamic_loading/2");
    }

    public void clickStart() {
        click(startButton);
    }

    public void waitForLoadingToFinish() {
        waitUtils.waitForInvisibility(loadingIndicator);
        waitUtils.waitForVisible(finishText);
    }

    public String getFinishText() {
        return getText(finishText);
    }

    public boolean isFinishTextDisplayed() {
        return isDisplayed(finishText);
    }
}
