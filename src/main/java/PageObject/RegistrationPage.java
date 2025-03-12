package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {

    private static final By NAME_FIELD = By.xpath(".//label[text()='Имя']/parent::*/input");
    private static final By EMAIL_FIELD = By.xpath(".//label[text()='Email']/parent::*/input");
    private static final By PASSWORD_FIELD = By.xpath(".//label[text()='Пароль']/parent::*/input");
    private static final By REGISTER_BUTTON = By.xpath(".//button[text()='Зарегистрироваться']");
    private static final By LOGIN_LINK = By.className("Auth_link__1fOlj");
    private static final By PASSWORD_FIELD_ERROR_TEXT = By.xpath(".//*[text()='Некорректный пароль']");

    private final WebDriver driver;

    public RegistrationPage(WebDriver driver){
        this.driver = driver;
    }

    public void waitForElementLoad()
    {
        //webDriver.findElement(NAME_FIELD).wait();
    }
    private void inputName(String name){
        driver.findElement(NAME_FIELD).sendKeys(name);
    }
    private void inputEmail(String email){
        driver.findElement(EMAIL_FIELD).sendKeys(email);
    }
    private void inputPassword(String password){
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
    }
    private void registerButtonClick(){
        driver.findElement(REGISTER_BUTTON).click();
    }
    public void registrationSequence(String email, String password, String name){
        inputName(name);
        inputEmail(email);
        inputPassword(password);
        registerButtonClick();
    }

    public void loginLinkClick(){
        driver.findElement(LOGIN_LINK).click();
    }
    public boolean isPasswordFieldErrorVisible(){
        return driver.findElement(PASSWORD_FIELD_ERROR_TEXT).isDisplayed();
    }
    //error message for wrong password length test
}
