package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BrokenImagesPage extends BasePage {

  private final By images = By.cssSelector(".example img");

  public BrokenImagesPage(WebDriver driver) {
    super(driver);
  }

  public void open() {
    openPath("/broken_images");
  }

  public int getImageCount() {
    return findAll(images).size();
  }

  public int getBrokenImageCount() {
    int brokenCount = 0;
    List<WebElement> imageElements = findAll(images);

    for (WebElement image : imageElements) {
      if (isImageBroken(image)) {
        brokenCount++;
      }
    }

    return brokenCount;
  }

  public boolean hasBrokenImages() {
    return getBrokenImageCount() > 0;
  }

  private boolean isImageBroken(WebElement image) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    Object result =
        js.executeScript(
            "return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0;",
            image);

    return !Boolean.TRUE.equals(result);
  }
}
