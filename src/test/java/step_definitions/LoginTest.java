package step_definitions;

import driver.BaseClass;
import io.cucumber.java.en.Given;
import org.junit.Assert;
import pages.LoginPage;

public class LoginTest {

    private LoginPage loginPage;

    public LoginTest() {
        this.loginPage = new LoginPage();
    }

    @Given("user is on home page")
    public void displayHomePage() {
        loginPage.displayLoginLogo();
    }

    @Given("I have entered valid username {string} and password {string}")
    public void enterValidUserNamePassword(String username, String password) {
        loginPage.enterUserName(username);
        loginPage.enterPassword(password);

    }

    @Given("I click on the login button")
    public void clickLoginButton() {
        loginPage.clickOnLogin();

    }

    @Given("The title of the page should be {string}")
    public void pageTitle(String ExpectedTitlename) {
        String title = loginPage.getLoginPageTitle();
        System.out.println("name of the title: " + title);
        Assert.assertTrue(title.contains(ExpectedTitlename));
        BaseClass.takeScreenshot("the_title_of_the_page_should_be", "chrome");
    }

}
