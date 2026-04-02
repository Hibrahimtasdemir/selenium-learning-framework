package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StatusCodesPage extends BasePage {

  private final By statusMessage = By.cssSelector(".example p");

  public StatusCodesPage(WebDriver driver) {
    super(driver);
  }

  public void open() {
    openPath("/status_codes");
  }

  public void clickStatusCodeLink(int statusCode) {
    click(By.linkText(String.valueOf(statusCode)));
  }

  public String getStatusMessage() {
    return getText(statusMessage);
  }
}
