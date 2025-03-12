package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private static final By REGISTER_LINK = By.xpath(".//*[text()='Зарегистрироваться']");
    private static final By LOGIN_BUTTON = By.xpath(".//button[text()='Войти']");
    private static final By EMAIL_FIELD = By.xpath(".//label[text()='Email']/parent::*/input");
    private static final By PASSWORD_FIELD = By.xpath(".//label[text()='Пароль']/parent::*/input");


    private final WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver=driver;
    }

    public void registerLinkClick(){
        driver.findElement(REGISTER_LINK).click();
    }
    private void waitForPageLoad(){
        new WebDriverWait(driver,5).until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
    }
    public boolean isPageOpened(){
        try {
            waitForPageLoad();
        }
        catch (RuntimeException e){
            return false;
        }
        return driver.findElement(LOGIN_BUTTON).isDisplayed();
    }

    private void inputEmail(String email){
        new WebDriverWait(driver,3).until(ExpectedConditions.presenceOfElementLocated(EMAIL_FIELD));
        driver.findElement(EMAIL_FIELD).sendKeys(email);
    }
    private void inputPassword(String password){
        new WebDriverWait(driver,3).until(ExpectedConditions.presenceOfElementLocated(PASSWORD_FIELD));
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
    }
    private void loginButtonClick(){
        driver.findElement(LOGIN_BUTTON).click();
    }
    public void loginPageSequence(String email,String password){
        inputEmail(email);
        inputPassword(password);
        loginButtonClick();
    }
}
