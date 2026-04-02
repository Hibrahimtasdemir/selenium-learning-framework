package pages;

import org.openqa.selenium.WebDriver;

public class NavigationPage extends BasePage {

  public NavigationPage(WebDriver driver) {
    super(driver);
  }

  public void openHomePage() {
    openPath("/");
    waitForTitle("The Internet");
  }

  public void goToLoginPage() {
    navigateToPath("/login");
    waitForUrlContains("/login");
  }

  public void goBackToHomePage() {
    navigateBack();
    waitForTitle("The Internet");
  }

  public void goForwardToLoginPage() {
    navigateForward();
    waitForUrlContains("/login");
  }

  public void refreshLoginPage() {
    refreshPage();
    waitForUrlContains("/login");
  }

  public String getPageTitle() {
    return driver.getTitle();
  }

  public String getCurrentUrl() {
    return driver.getCurrentUrl();
  }

  private void waitForTitle(String title) {
    waitUtils.waitForTitleIs(title);
  }

  private void waitForUrlContains(String text) {
    waitUtils.waitForUrlContains(text);
  }
}
