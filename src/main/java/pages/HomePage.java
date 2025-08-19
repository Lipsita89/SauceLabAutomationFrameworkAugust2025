package pages;

import driver.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends BaseClass {
    private final By PRODUCT_LIST = By.xpath("//div[@class='inventory_item_description']");
    private final By CART = By.xpath("//a[@class='shopping_cart_link']");
    private final By ADD_TO_CART = By.xpath(".//button[contains(text(), 'Add to cart')]");
    private final By ADDED_PRODUCT = By.xpath("//div[@class='inventory_item_name']");

    public HomePage() {
    }

    public void getHomepageTitle() {
        System.out.println("Home page title: " + getTitle());
    }

    public void displayAllProductTexts() {
        System.out.println("Displaying all products:");
        for (WebElement product : driver.findElements(PRODUCT_LIST)) {
            System.out.println(product.getText());
            System.out.println("checking");
        }
        try {
            Robot robot = new Robot();
            robot.delay(2000);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void findItemHighestPrice() {
        System.out.println("Finding product with the highest price...");

        List<Product> products = extractProducts();
        Product highest = findHighestPricedProduct(products);

        if (highest != null) {
            System.out.println("Highest priced product: " + highest.name + " - $" + highest.price);
            return;
        }

        System.out.println("No products found.");
    }

    public void addProductToCartHighestPrice() {
        System.out.println("Adding highest priced product to cart...");
        List<Product> products = extractProducts();
        Product highest = findHighestPricedProduct(products);

        if (highest != null && highest.element != null) {
            try {
                WebElement addToCartButton = highest.element.findElement(ADD_TO_CART);
                addToCartButton.click();
                System.out.println("Added to cart: " + highest.name + " - $" + highest.price);
            } catch (NoSuchElementException e) {
                System.out.println("Add to cart button not found for: " + highest.name);
            }
        } else {
            System.out.println("No products found or unable to locate highest.");
        }
    }

    public void validateProductInCart(String expectedProductName) {
        System.out.println("Validating product in cart...");

        String count = driver.findElement(CART).getText();
        System.out.println("Cart count: " + count);
        clickWhenReady(CART, 5);
        WebElement cartProduct = driver.findElement(ADDED_PRODUCT);
        String cartProductName = cartProduct.getText();

        if (cartProductName.equals(expectedProductName)) {
            System.out.println("Validation PASSED: Product is correctly added to the cart.");
        } else {
            System.out.println("Validation FAILED: Expected '" + expectedProductName + "', but found '" + cartProductName + "'");
        }
    }

    private static class Product {
        String name;
        double price;
        WebElement element;

        Product(String name, double price, WebElement element) {
            this.name = name;
            this.price = price;
            this.element = element;
        }
    }

    private List<Product> extractProducts() {
        List<Product> products = new ArrayList<>();

        for (WebElement product : driver.findElements(PRODUCT_LIST)) {
            String[] lines = product.getText().split("\n");

            if (lines.length == 0) continue;

            String name = lines[0];
            double price = 0.0;

            for (String line : lines) {
                if (line.contains("$")) {
                    try {
                        price = parsePrice(line);
                        break;
                    } catch (NumberFormatException ignored) {
                    }
                }
            }

            products.add(new Product(name, price, product));
        }

        return products;
    }

    private double parsePrice(String text) throws NumberFormatException {
        return Double.parseDouble(text.replace("$", "").replace(",", "").trim());
    }

    private Product findHighestPricedProduct(List<Product> products) {
        Product highest = null;
        double maxPrice = 0.0;

        for (Product product : products) {
            if (product.price > maxPrice) {
                maxPrice = product.price;
                highest = product;
            }
        }

        return highest;
    }
}
