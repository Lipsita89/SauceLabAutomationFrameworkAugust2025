package hooks;

import driver.BaseClass;
import driver.WebDriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    BaseClass baseClass = new BaseClass();

    @Before
    public void initBrowser() {
        baseClass.initializeBrowser();
    }

    @After
    public void tearDown(Scenario scenario) {
        baseClass.attachScreenshot(scenario);
        WebDriverFactory.getInstance().quitDriver();
    }
}
