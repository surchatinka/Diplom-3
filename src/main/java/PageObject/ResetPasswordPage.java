package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ResetPasswordPage {
    private static final By LOGIN_LINK = By.className("Auth_link__1fOlj");

    private final WebDriver driver;

    public ResetPasswordPage(WebDriver driver){
        this.driver = driver;
    }

    public void loginLinkClick(){
        driver.findElement(LOGIN_LINK).click();
    }
}
