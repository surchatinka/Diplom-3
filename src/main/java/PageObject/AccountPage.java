package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountPage {
    private static final By PROFILE_SECTION_TEXT = By.xpath(".//*[@href='/account/profile']");
    private static final By LOGOUT_BUTTON = By.linkText("Выход");
        //$x(".//*[text()='Выход']")

    private final WebDriver driver;
    public AccountPage(WebDriver driver){
        this.driver=driver;
    }

    public boolean isPageLoaded(){
        new WebDriverWait(driver,3).until(ExpectedConditions.presenceOfElementLocated(PROFILE_SECTION_TEXT));
        return driver.findElement(PROFILE_SECTION_TEXT).isDisplayed();
    }
    public void logoutButtonClick(){
        driver.findElement(LOGOUT_BUTTON).click();
    }
}
