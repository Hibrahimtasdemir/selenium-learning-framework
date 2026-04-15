package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.components.AlertComponent;

public class AlertsPage extends BasePage {

  @FindBy(id = "result")
  private WebElement result;

  private final By jsAlertButton = By.xpath("//button[text()='Click for JS Alert']");
  private final By jsConfirmButton = By.xpath("//button[text()='Click for JS Confirm']");
  private final By jsPromptButton = By.xpath("//button[text()='Click for JS Prompt']");

  private final AlertComponent alertComponent = new AlertComponent();

  public AlertsPage(WebDriver driver) {
    super(driver);
    PageFactory.initElements(driver, this);
  }

  public void open() {
    openPath("/javascript_alerts");
  }

  public void openJsAlert() {
    click(jsAlertButton);
  }

  public void acceptJsAlert() {
    click(jsAlertButton);
    alertComponent.acceptAlert();
  }

  public void openJsConfirm() {
    click(jsConfirmButton);
  }

  public void dismissJsConfirm() {
    click(jsConfirmButton);
    alertComponent.dismissAlert();
  }

  public void openJsPrompt() {
    click(jsPromptButton);
  }

  public void sendTextToPrompt(String text) {
    click(jsPromptButton);
    alertComponent.sendTextToAlert(text);
  }

  public String getResultText() {
    return result.getText();
  }

  public String getResultMessage() {
    return getResultText();
  }

  public boolean isResultMessageDisplayed(String text) {
    return waitUtils.waitForTextContains(By.id("result"), text);
  }
}
