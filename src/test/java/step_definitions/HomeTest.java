package step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.HomePage;
import pages.LoginPage;

public class HomeTest {
    private final LoginPage loginpage = new LoginPage();
    private final HomePage homePage = new HomePage();

    @Given("I have logged in with valid credentials")
    public void login() {
        loginpage.doLogin("standard_user", "secret_sauce");
    }

    @Given("I am on the HomePage")
    public void displayHomePage() {
        homePage.getHomepageTitle();
    }

    @When("I Select the highest price item")
    public void selectHighestPricedItem() {
        homePage.displayAllProductTexts();
        homePage.findItemHighestPrice();

    }

    @When("I add the selected highest price item to the cart")
    public void addProductToBasket() {
        homePage.addProductToCartHighestPrice();
    }

    @Then("the item is added in the cart {string}")
    public void validateAddedProduct(String expectedProductName) {
        homePage.validateProductInCart(expectedProductName);

    }

}
