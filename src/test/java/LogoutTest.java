import PageObject.AccountPage;
import PageObject.LoginPage;
import client.StellarBurgerClient;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Token;
import model.User;
import model.WebDriverFactory;
import net.datafaker.Faker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Locale;

public class LogoutTest {

   private WebDriver driver;
   private Token token;
   private final StellarBurgerClient client = new StellarBurgerClient();

   @Test
   @DisplayName("Logout test")
   @Description("Test checks how logout works")
   public void logoutFromAccountPageTest(){
       driver.get("https://stellarburgers.nomoreparties.site/account");
       AccountPage accountPage = new AccountPage(driver);
       accountPage.logoutButtonClick();
       LoginPage loginPage = new LoginPage(driver);
       boolean result = loginPage.isPageOpened();
       Assert.assertTrue(result);
    }

   @Before
   @Step("Test preparation")
   public void startBrowser() {
        driver = WebDriverFactory.createWebDriver();
        Faker faker = new Faker(new Locale("en"));
        String email = faker.internet().emailAddress();
        String name = faker.name().firstName();
        String password =  faker.bothify("??##??##");
        User user = new User(email,password,name);
        ValidatableResponse response = client.createUser(user);
        token = client.getToken(response);
        driver.get("https://stellarburgers.nomoreparties.site/account");
        JavascriptExecutor exec = (JavascriptExecutor)driver;
        String scriptAccessToken = String.format("window.localStorage.setItem('accessToken', '%s')",token.getAccessToken());
        String scriptRefreshToken = String.format("window.localStorage.setItem('refreshToken', '%s')",token.getRefreshToken());
        exec.executeScript(scriptAccessToken);
        exec.executeScript(scriptRefreshToken);
        driver.navigate().refresh();
    }

   @After
   @Step("Clean data and shutdown")
   public void tearDown() {
        driver.quit();
        if (token.getAccessToken()!=null){
            client.deleteUser(token);
        }
    }
}
