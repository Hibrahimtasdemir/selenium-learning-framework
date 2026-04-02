package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class ContextMenuPage extends BasePage {

  private final By hotSpot = By.id("hot-spot");

  public ContextMenuPage(WebDriver driver) {
    super(driver);
  }

  public void open() {
    openPath("/context_menu");
  }

  public void rightClickHotSpot() {
    new Actions(driver).contextClick(find(hotSpot)).perform();
    pauseForDemo();
  }

  public String getAlertText() {
    return waitUtils.waitForAlert().getText();
  }

  public void acceptAlert() {
    waitUtils.waitForAlert().accept();
    pauseForDemo();
  }
}
