package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AlertsPage extends BasePage {

  private final By jsAlertButton = By.xpath("//button[text()='Click for JS Alert']");
  private final By jsConfirmButton = By.xpath("//button[text()='Click for JS Confirm']");
  private final By jsPromptButton = By.xpath("//button[text()='Click for JS Prompt']");
  private final By resultMessage = By.id("result");

  public AlertsPage(WebDriver driver) {
    super(driver);
  }

  public void open() {
    openPath("/javascript_alerts");
  }

  public void acceptJsAlert() {
    click(jsAlertButton);
    waitUtils.waitForAlert().accept();
  }

  public void dismissJsConfirm() {
    click(jsConfirmButton);
    waitUtils.waitForAlert().dismiss();
  }

  public void sendTextToPrompt(String text) {
    click(jsPromptButton);
    Alert alert = waitUtils.waitForAlert();
    alert.sendKeys(text);
    alert.accept();
  }

  public String getResultMessage() {
    return getText(resultMessage);
  }

  public boolean isResultMessageDisplayed(String text) {
    return waitUtils.waitForTextContains(resultMessage, text);
  }
}
