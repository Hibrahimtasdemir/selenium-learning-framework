package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DemoUtils;
import utils.WaitUtils;

public class BaseTest {

    protected WebDriver driver;
    protected WaitUtils waitUtils;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        waitUtils = new WaitUtils(driver);
        pauseForDemo();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            pauseForDemo();
            driver.quit();
        }
    }

    protected void pauseForDemo() {
        DemoUtils.pause();
    }
}
