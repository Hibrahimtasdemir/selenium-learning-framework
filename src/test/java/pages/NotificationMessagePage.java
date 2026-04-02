package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;

public class NotificationMessagePage extends BasePage {

    private final By clickHereLink = By.linkText("Click here");
    private final By notificationMessage = By.id("flash");

    public NotificationMessagePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPath("/notification_message_rendered");
    }

    public void clickHere() {
        click(clickHereLink);
    }

    public String getNotificationMessage() {
        String rawText = getText(notificationMessage);
        return rawText.replace("×", "").trim();
    }

    public boolean isNotificationOneOf(String... expectedMessages) {
        String actualMessage = getNotificationMessage();
        return Arrays.asList(expectedMessages).contains(actualMessage);
    }
}
