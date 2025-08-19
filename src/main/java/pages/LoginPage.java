package pages;

import driver.BaseClass;
import org.openqa.selenium.By;

public class LoginPage extends BaseClass {
    
    private final By USERNAME = By.id("user-name");
    private final By PASSWORD = By.id("password");
    private final By LOGIN_BUTTON = By.id("login-button");
    private final By LOGIN_LOGO = By.xpath("//div[@class='login_logo']");

    public void displayLoginLogo() {
        isElementPresent(LOGIN_LOGO);
    }
    public String getLoginPageTitle() {
        return getTitle();
    }

    public void enterUserName(String username) {
        sendData(driver.findElement(USERNAME), username);
    }

    public void enterPassword(String pwd) {
        sendData(driver.findElement(PASSWORD), pwd);
    }

    public void clickOnLogin() {
        clickWhenReady(LOGIN_BUTTON, 5);
    }

    public HomePage doLogin(String un, String pwd) {
        driver.findElement(USERNAME).sendKeys(un);
        driver.findElement(PASSWORD).sendKeys(pwd);
        driver.findElement(LOGIN_BUTTON).click();
        return new HomePage();
    }

}
