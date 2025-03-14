package PageObject;

import io.qameta.allure.Step;
import model.ConstructorOpenWay;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountPage {
    private static final By PROFILE_SECTION_TEXT = By.xpath(".//*[@href='/account/profile']");
    private static final By LOGOUT_BUTTON = By.xpath(".//*[text()='Выход']");
    private static final By STELLAR_BURGER_LOGO = By.xpath(".//*[contains(@class,'logo')]");
    private static final By CONSTRUCTOR_BUTTON = By.linkText("Конструктор");

    private final WebDriver driver;
    public AccountPage(WebDriver driver){
        this.driver=driver;
    }

    @Step("Check is page loaded")
    public boolean isPageLoaded(){
        new WebDriverWait(driver,3).until(ExpectedConditions.presenceOfElementLocated(PROFILE_SECTION_TEXT));
        return driver.findElement(PROFILE_SECTION_TEXT).isDisplayed();
    }
    @Step("Click to logout button")
    public void logoutButtonClick(){
        new WebDriverWait(driver,3).until(ExpectedConditions.presenceOfElementLocated(LOGOUT_BUTTON));
        driver.findElement(LOGOUT_BUTTON).click();
    }
    @Step("Click to constructor open button")
    public void openConstructor(ConstructorOpenWay button){
        switch(button){
            case LOGO:
                driver.findElement(STELLAR_BURGER_LOGO).click();
                break;
            case BUTTON:
                driver.findElement(CONSTRUCTOR_BUTTON).click();
                break;
            default:
                throw new IllegalArgumentException("No such button exist - "+button);
        }
    }
}
