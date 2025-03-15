package ru.page.object;

import io.qameta.allure.Step;
import ru.model.BurgerSection;
import ru.model.LoginWay;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {

    private static final By LOGIN_BUTTON = By.xpath(".//button[text()='Войти в аккаунт']");
    private static final By ACCOUNT_BUTTON = By.xpath(".//*[text()='Личный Кабинет']");
    private static final By MAKE_ORDER_BUTTON = By.xpath(".//button[text()='Оформить заказ']");
    private static final By MAKE_A_BURGER_TEXT = By.xpath(".//*[text()='Соберите бургер']");
    private static final By BUN_SECTION_TAB = By.xpath(".//span[text()='Булки']/parent::*");
    private static final By SAUCE_SECTION_TAB = By.xpath(".//span[text()='Соусы']/parent::*");
    private static final By INGREDIENT_SECTION_TAB = By.xpath(".//span[text()='Начинки']/parent::*");
    private final WebDriver driver;

    public MainPage(WebDriver driver){
        this.driver=driver;
    }

    @Step("Click to login button")
    public void loginPageOpenWith(LoginWay way){
       switch (way){
           case MAIN_PAGE_LOGIN_BUTTON: driver.findElement(LOGIN_BUTTON).click(); break;
           case MAIN_PAGE_ACCOUNT_BUTTON: driver.findElement(ACCOUNT_BUTTON).click(); break;
           default: throw new IllegalArgumentException("Not a main page login way");
       }
    }
    @Step("Check is page loaded")
    public boolean isMakeOrderButtonDisplayed(){
        new WebDriverWait(driver,3).until(ExpectedConditions.presenceOfElementLocated(MAKE_ORDER_BUTTON));
        return driver.findElement(MAKE_ORDER_BUTTON).isDisplayed();
    }
    @Step("Check is constructor opened")
    public boolean isConstructorOpens(){
        new WebDriverWait(driver,3).until(ExpectedConditions.presenceOfElementLocated(MAKE_A_BURGER_TEXT));
        return driver.findElement(MAKE_A_BURGER_TEXT).isDisplayed();
    }
    @Step("Check is element viewable")
    public boolean isElementInViewport(BurgerSection burgerSection){
        switch(burgerSection){
            case BUN:
                return isBunSectionInViewport();
            case SAUCE:
                return isSauceSectionInViewport();
            case INGREDIENTS:
                return isIngredientSectionInViewport();
            default:
                throw new IllegalArgumentException("No such section in burger constructor- " + burgerSection);
        }
    }
    @Step("Check is bun section viewable")
    private boolean isBunSectionInViewport() {
        return driver.findElement(BUN_SECTION_TAB).getAttribute("class").contains("tab_tab_type_current__2BEPc");
    }
    @Step("Check is sauce section viewable")
    private boolean isSauceSectionInViewport() {
        driver.findElement(SAUCE_SECTION_TAB).click();
        return driver.findElement(SAUCE_SECTION_TAB).getAttribute("class").contains("tab_tab_type_current__2BEPc");
    }
    @Step("Check is ingredient section viewable")
    private boolean isIngredientSectionInViewport() {
        driver.findElement(INGREDIENT_SECTION_TAB).click();
        return driver.findElement(INGREDIENT_SECTION_TAB).getAttribute("class").contains("tab_tab_type_current__2BEPc");
    }
}
