package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class HorizontalSliderPage extends BasePage {

    private final By slider = By.cssSelector("input[type='range']");
    private final By rangeValue = By.id("range");

    public HorizontalSliderPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPath("/horizontal_slider");
    }

    public void moveSliderRight(int steps) {
        for (int i = 0; i < steps; i++) {
            find(slider).sendKeys(Keys.ARROW_RIGHT);
            pauseForDemo();
        }
    }

    public String getSliderValue() {
        return getText(rangeValue);
    }
}
