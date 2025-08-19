package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.safari.SafariDriver;
import util.PropertyUtils;

import java.util.concurrent.TimeUnit;

public class WebDriverFactory {
    private static final Logger log = LogManager.getLogger(WebDriverFactory.class.getName());
    public static PropertyUtils prop = PropertyUtils.getInstance();
    private static final WebDriverFactory instance = new WebDriverFactory();

    private WebDriverFactory() {
    }

    public static WebDriverFactory getInstance() {
        return instance;
    }

    public static ThreadLocal<WebDriver> threadedDriver = new ThreadLocal<>();

    public WebDriver initDriver() {
        String browser = prop.getPropertyValue("browser");
        String isHeadless = prop.getPropertyValue("headless");

        try {
            if (browser.equalsIgnoreCase("FIREFOX")) {
                FirefoxOptions ffOptions = setFFOptions();
                if (Boolean.parseBoolean(isHeadless)) {
                    ffOptions.addArguments("--headless");
                }
                WebDriverManager.firefoxdriver().setup();
                threadedDriver.set(new FirefoxDriver(ffOptions));
            } else if (browser.equalsIgnoreCase("CHROME")) {
                ChromeOptions chromeOptions = setChromeOptions();
                if (Boolean.parseBoolean(isHeadless)) {
                    chromeOptions.addArguments("--headless");
                }
                WebDriverManager.chromedriver().setup();
                threadedDriver.set(new ChromeDriver(chromeOptions));
            } else if (browser.equalsIgnoreCase("IE")) {
                InternetExplorerOptions ieOptions = setIEOptions();
                WebDriverManager.iedriver().setup();
                threadedDriver.set(new InternetExplorerDriver(ieOptions));
            } else if (browser.equalsIgnoreCase("SAFARI")) {
                WebDriverManager.safaridriver().setup();
                threadedDriver.set(new SafariDriver());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        threadedDriver.get().manage().deleteAllCookies();
        threadedDriver.get().manage().timeouts().implicitlyWait(
                Integer.parseInt(prop.getPropertyValue("implicitWaitTime")), TimeUnit.SECONDS);
        threadedDriver.get().manage().window().maximize();

        return threadedDriver.get();
    }

    public synchronized WebDriver getDriver() {
        return threadedDriver.get();
    }

    public void quitDriver() {
        if (threadedDriver.get() != null) {
            threadedDriver.get().quit();
            threadedDriver.remove();
        }
    }

    private ChromeOptions setChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");
        options.addArguments("disable-restore-session-state");
        return options;
    }

    private FirefoxOptions setFFOptions() {
        return new FirefoxOptions();
    }

    private InternetExplorerOptions setIEOptions() {
        InternetExplorerOptions options = new InternetExplorerOptions();
        options.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
        options.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
        options.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
        options.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        return options;
    }
}
