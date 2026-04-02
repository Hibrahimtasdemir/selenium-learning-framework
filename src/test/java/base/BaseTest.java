package base;

import driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DemoUtils;
import utils.WaitUtils;

public class BaseTest {

    protected WebDriver driver;
    protected WaitUtils waitUtils;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverFactory.createDriver();
        driver = DriverFactory.getDriver();
        waitUtils = new WaitUtils(driver);
        pauseForDemo();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            pauseForDemo();
            DriverFactory.quitDriver();
        }
    }

    protected void pauseForDemo() {
        DemoUtils.pause();
    }
}
