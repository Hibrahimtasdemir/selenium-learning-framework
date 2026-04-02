package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InfiniteScrollPage extends BasePage {

  private final By paragraphs = By.cssSelector(".jscroll-added .jscroll-added, .jscroll-added");
  private static final int DEFAULT_SCROLL_ATTEMPTS = 6;
  private static final Duration INCREMENT_WAIT_TIMEOUT = Duration.ofSeconds(3);

  public InfiniteScrollPage(WebDriver driver) {
    super(driver);
  }

  public void open() {
    openPath("/infinite_scroll");
  }

  public int getParagraphCount() {
    return driver.findElements(paragraphs).size();
  }

  public void scrollToBottom() {
    ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    pauseForDemo();
  }

  public boolean hasParagraphCountIncreased(int previousCount) {
    return getParagraphCount() > previousCount;
  }

  public boolean scrollUntilParagraphCountIncreases(int previousCount) {
    return scrollUntilParagraphCountIncreases(previousCount, DEFAULT_SCROLL_ATTEMPTS);
  }

  public boolean scrollUntilParagraphCountIncreases(int previousCount, int maxAttempts) {
    for (int attempt = 1; attempt <= maxAttempts; attempt++) {
      scrollToBottom();

      if (waitForParagraphCountIncrease(previousCount)) {
        return true;
      }
    }

    return false;
  }

  private boolean waitForParagraphCountIncrease(int previousCount) {
    try {
      return new WebDriverWait(driver, INCREMENT_WAIT_TIMEOUT)
          .until(d -> getParagraphCount() > previousCount);
    } catch (TimeoutException e) {
      return false;
    }
  }
}
