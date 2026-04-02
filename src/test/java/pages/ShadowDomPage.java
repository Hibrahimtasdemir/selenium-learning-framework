package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ShadowDomPage extends BasePage {

  private final By paragraphHosts = By.cssSelector("my-paragraph");

  public ShadowDomPage(WebDriver driver) {
    super(driver);
  }

  public void open() {
    openPath("/shadowdom");
  }

  public int getParagraphHostCount() {
    return findAll(paragraphHosts).size();
  }

  public String getShadowParagraphText(int hostIndex) {
    SearchContext shadowRoot = getParagraphHost(hostIndex).getShadowRoot();
    return shadowRoot.findElement(By.cssSelector("p")).getText();
  }

  private WebElement getParagraphHost(int hostIndex) {
    List<WebElement> hosts = findAll(paragraphHosts);

    if (hostIndex < 1 || hostIndex > hosts.size()) {
      throw new IllegalArgumentException("Invalid shadow host index: " + hostIndex);
    }

    return hosts.get(hostIndex - 1);
  }
}
