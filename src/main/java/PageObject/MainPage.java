package PageObject;


import model.LoginWay;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {

    private static final By LOGIN_BUTTON = By.xpath(".//button[text()='Войти в аккаунт']");
    private static final By ACCOUNT_BUTTON = By.xpath(".//*[text()='Личный Кабинет']");
    private static final By MAKE_ORDER_BUTTON = By.xpath(".//button[text()='Оформить заказ']");
    private static final By MAKE_A_BURGER_TEXT = By.xpath(".//*[text()='Соберите бургер']");

    private final WebDriver driver;

    public MainPage(WebDriver driver){
        this.driver=driver;
    }

    public void loginPageOpenWith(LoginWay way){
       switch (way){
           case MAIN_LOGIN: driver.findElement(LOGIN_BUTTON).click(); break;
           case MAIN_ACCOUNT: driver.findElement(ACCOUNT_BUTTON).click(); break;
           default: throw new RuntimeException("Not a main page login way");
       }
    }
    public boolean isMakeOrderButtonDisplayed(){
        new WebDriverWait(driver,3).until(ExpectedConditions.presenceOfElementLocated(MAKE_ORDER_BUTTON));
        return driver.findElement(MAKE_ORDER_BUTTON).isDisplayed();
    }
    public boolean isContructorOpens(){
        new WebDriverWait(driver,3).until(ExpectedConditions.presenceOfElementLocated(MAKE_A_BURGER_TEXT));
        return driver.findElement(MAKE_A_BURGER_TEXT).isDisplayed();
    }
}
