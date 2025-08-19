package driver;

import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.PropertyUtils;
import util.Util;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaseClass {
    private static final Logger log = LogManager.getLogger(BaseClass.class.getName());
    public static WebDriver driver;
    public JavascriptExecutor js;
    public PropertyUtils prop;

    public BaseClass() {
        this.driver = WebDriverFactory.getInstance().getDriver();
        this.prop = new PropertyUtils();
        this.js = (JavascriptExecutor) driver;
    }

    public void initializeBrowser() {
        driver = WebDriverFactory.getInstance().initDriver();
        driver.get(prop.getPropertyValue("baseUrl"));
    }

    public String getTitle() {
        String title = driver.getTitle();
        log.info("Title of the page is :: " + title);
        return title;
    }

    public String getURL() {
        String url = driver.getCurrentUrl();
        log.info("Current URL is :: " + url);
        return url;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public By getByType(String locator) {
        By by = null;
        String locatorType = locator.split("=>")[0];
        locator = locator.split("=>")[1];
        try {
            if (locatorType.contains("id")) {
                by = By.id(locator);
            } else if (locatorType.contains("name")) {
                by = By.name(locator);
            } else if (locatorType.contains("xpath")) {
                by = By.xpath(locator);
            } else if (locatorType.contains("css")) {
                by = By.cssSelector(locator);
            } else if (locatorType.contains("class")) {
                by = By.className(locator);
            } else if (locatorType.contains("tag")) {
                by = By.tagName(locator);
            } else if (locatorType.contains("link")) {
                by = By.linkText(locator);
            } else if (locatorType.contains("partiallink")) {
                by = By.partialLinkText(locator);
            } else {
                log.info("Locator type not supported");
            }
        } catch (Exception e) {
            log.error("Element not found with: {} error Message - {}", locator, e.toString());
        }
        return by;
    }

    public WebElement getElement(String locator) {
        WebElement element = null;
        By byType = getByType(locator);
        try {
            element = driver.findElement(byType);
        } catch (Exception e) {
            log.error("Element not found with: {} error Message - {}", locator, e.toString());
        }
        return element;
    }

    public WebElement getElement(By locator) {
        WebElement element = null;
        try {
            element = driver.findElement(locator);
        } catch (Exception e) {
            log.error("Element not found with: {} error Message - {}", locator, e.toString());
        }
        return element;
    }

    public List<WebElement> getElementList(By locator) {
        List<WebElement> elementList = new ArrayList<WebElement>();
        try {
            elementList = driver.findElements(locator);
            if (!elementList.isEmpty()) {
                log.info("Element List found with: " + locator);
            } else {
                log.info("Element List not found with: " + locator);
            }
        } catch (Exception e) {
            log.error("Element not found with: {} error Message - {}", locator, e.toString());
        }
        return elementList;
    }

    public boolean isElementPresent(By locator) {
        List<WebElement> elementList = getElementList(locator);
        int size = elementList.size();
        if (size > 0) {
            log.info("Element Present with locator " + locator);
            return true;
        } else {
            log.info("Element Not Present with locator " + locator);
            return false;
        }
    }

    public void elementClick(WebElement element, long timeToWait) {
        try {
            element.click();
            if (timeToWait == 0) {
                log.info("Clicked On element");
            } else {
                Util.sleep(timeToWait, "Clicked on element ");
            }
        } catch (Exception e) {
            log.error("Cannot click on Element " + e);
            takeScreenshot("Click ERROR", "");
        }
    }

    public void elementClick(By locator) {
        elementClick(this.getElement(locator), 0);
    }

    public void clickWhenReady(By locator, int timeout) {
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            WebElement element;
            log.info("Waiting for max:: " + timeout + " seconds for element to be clickable");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(500L));
            element = wait.until(
                    ExpectedConditions.elementToBeClickable(locator));
            element.click();
            log.info("Element clicked on the web page");
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Element not appeared on the web page");
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        }
    }

    public void sendData(WebElement element, String data) {
        try {
            element.clear();
            //Util.sleep(1000, "Waiting Before Entering Data");
            element.sendKeys(data);
            log.info("Send Keys on element with data :: " + data);
        } catch (Exception e) {
            log.error("Cannot send keys on element with data :: " + data);
        }
    }

    public void sendDataToLocator(By locator, String data) {
        sendData(this.getElement(locator), data);
    }

    public String getText(WebElement element, String info) {
        System.out.println("Getting Text on element :: " + info);
        String text = null;
        text = element.getText();
        if (text.length() == 0) {
            text = element.getAttribute("innerText");
        }
        if (!text.isEmpty()) {
            log.info("The text is : " + text);
        } else {
            log.error("Text Not Found");
        }
        return text.trim();
    }

    public Boolean isDisplayed(WebElement element) {
        Boolean displayed = false;
        if (element != null) {
            displayed = element.isDisplayed();
            if (displayed)
                log.info("Element is displayed");
            else
                log.info("Element is NOT displayed");
        }
        return displayed;
    }

    public static String takeScreenshot(String methodName, String browserName) {
        String fileName = Util.getScreenshotName(methodName, browserName);
        String screenshotDir = System.getProperty("user.dir") + "/screenshots/";
        new File(screenshotDir).mkdirs();
        String path = screenshotDir + fileName;

        try {
            File screenshot = ((TakesScreenshot) driver).
                    getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(path));
            log.info("Screen Shot Was Stored at: " + path);
        } catch (Exception e) {
            log.error("An error occurred: ", e);
        }
        return path;
    }

    public void attachScreenshot(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot");
        }
    }

}
