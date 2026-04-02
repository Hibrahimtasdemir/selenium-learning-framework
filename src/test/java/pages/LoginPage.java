package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By usernameInput = By.id("username");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By flashMessage = By.id("flash");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("https://the-internet.herokuapp.com/login");
    }

    public void login(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
    }

    public String getFlashMessage() {
        return getText(flashMessage);
    }

    public boolean isLoginSuccessful() {
        return getFlashMessage().contains("You logged into a secure area!");
    }

    public boolean isUsernameErrorDisplayed() {
        return getFlashMessage().contains("Your username is invalid!");
    }

    public boolean isPasswordErrorDisplayed() {
        return getFlashMessage().contains("Your password is invalid!");
    }
}
