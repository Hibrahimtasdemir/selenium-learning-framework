package pages.components;

import driver.DriverFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class AlertComponent {
    private final WebDriver driver;
    private final WaitUtils waitUtils;

    public AlertComponent() {
        this.driver = DriverFactory.getDriver();
        this.waitUtils = new WaitUtils(driver);
    }

    public void acceptAlert() {
        Alert alert = waitUtils.waitForAlert();
        alert.accept();
    }

    public void dismissAlert() {
        Alert alert = waitUtils.waitForAlert();
        alert.dismiss();
    }

    public void sendTextToAlert(String text) {
        Alert alert = waitUtils.waitForAlert();
        alert.sendKeys(text);
        alert.accept();
    }

    public String getAlertText() {
        Alert alert = waitUtils.waitForAlert();
        String text = alert.getText();
        alert.accept();
        return text;
    }
}
