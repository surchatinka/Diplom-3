package model;

import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverFactory {

    private static final String WEBDRIVER_CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
    private static final String DEFAULT_BROWSER = "CHROME";

    public static WebDriver createWebDriver() {

        Browser browser = getActiveBrowser();
        WebDriverConfig webDriverConfig = ConfigFactory.create(WebDriverConfig.class);

        switch(browser) {
            case CHROME:
                System.setProperty(WEBDRIVER_CHROME_DRIVER_PROPERTY,webDriverConfig.chromeDriverPath());
                return new ChromeDriver();

            case YANDEX:
                System.setProperty(WEBDRIVER_CHROME_DRIVER_PROPERTY,webDriverConfig.yandexDriverPath());
                return new ChromeDriver();

            default:
                throw new IllegalArgumentException("Unsupported browser");
        }
    }
    private static Browser getActiveBrowser(){
        String browserName = System.getenv("browser");
        if (browserName.isEmpty()) {
            return Browser.valueOf(DEFAULT_BROWSER);
        }
        return Browser.valueOf(browserName.toUpperCase());
    }
}
