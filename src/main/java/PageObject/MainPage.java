package PageObject;


import model.BurgerSection;
import model.LoginWay;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {

    private static final By LOGIN_BUTTON = By.xpath(".//button[text()='Войти в аккаунт']");
    private static final By ACCOUNT_BUTTON = By.xpath(".//*[text()='Личный Кабинет']");
    private static final By MAKE_ORDER_BUTTON = By.xpath(".//button[text()='Оформить заказ']");
    private static final By MAKE_A_BURGER_TEXT = By.xpath(".//*[text()='Соберите бургер']");
    private static final By BUN_PIECE = By.xpath(".//img[@alt='Краторная булка N-200i']");
    private static final By SAUCE_PIECE = By.xpath(".//img[@alt='Соус фирменный Space Sauce']");
    private static final By INGREDIENT_PIECE = By.xpath(".//img[@alt='Говяжий метеорит (отбивная)']");
    private static final By BUN_SECTION_TAB = By.xpath(".//span[text()='Булки']/parent::*");
    private static final By SAUCE_SECTION_TAB = By.xpath(".//span[text()='Соусы']/parent::*");
    private static final By INGREDIENT_SECTION_TAB = By.xpath(".//span[text()='Начинки']/parent::*");
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
    public boolean isConstructorOpens(){
        new WebDriverWait(driver,3).until(ExpectedConditions.presenceOfElementLocated(MAKE_A_BURGER_TEXT));
        return driver.findElement(MAKE_A_BURGER_TEXT).isDisplayed();
    }

    public boolean isElementInViewport(BurgerSection burgerSection){
        WebElement element;
        switch(burgerSection){
            case BUN:
                element = driver.findElement(BUN_PIECE);
                if( (driver.findElement(BUN_SECTION_TAB).getAttribute("class")).contains("current")) {
                    driver.findElement(INGREDIENT_SECTION_TAB).click();
                }
                driver.findElement(BUN_SECTION_TAB).click();
                break;
            case INGREDIENTS:
                element = driver.findElement(INGREDIENT_PIECE);
                driver.findElement(INGREDIENT_SECTION_TAB).click();
                break;
            case SAUCE:
                element = driver.findElement(SAUCE_PIECE);
                driver.findElement(SAUCE_SECTION_TAB).click();
                break;
            default: throw new IllegalArgumentException("How you reached there??? Section don't exist - " + burgerSection);
        }

        return new WebDriverWait(driver, 3)
                .until(
                        driver -> {
                            Rectangle rect = element.getRect();
                            Dimension windowSize = driver.manage().window().getSize();
                            return rect.getX() >= 0
                                    && rect.getY() >= 0
                                    && rect.getX() + rect.getWidth() <= windowSize.getWidth()
                                    && rect.getY() + rect.getHeight() <= windowSize.getHeight();
                        });
    }
}
