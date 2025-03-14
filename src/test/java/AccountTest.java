import PageObject.AccountPage;
import PageObject.MainPage;
import client.StellarBurgerClient;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.LoginWay;
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

public class AccountTest {

    private Token token;
    private WebDriver driver;
    private final StellarBurgerClient client = new StellarBurgerClient();

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
        driver.get("https://stellarburgers.nomoreparties.site");
        JavascriptExecutor exec = (JavascriptExecutor)driver;
        String scriptAccessToken = String.format("window.localStorage.setItem('accessToken', '%s')",token.getAccessToken());
        String scriptRefreshToken = String.format("window.localStorage.setItem('refreshToken', '%s')",token.getRefreshToken());
        exec.executeScript(scriptAccessToken);
        exec.executeScript(scriptRefreshToken);
    }

    @After
    @Step("Clean data and shutdown")
    public void tearDown() {
        driver.quit();
        if (token.getAccessToken()!=null){
            client.deleteUser(token);
        }
    }

    @Test
    @DisplayName("Account page test")
    @Description("Check if account page opens")
    public void AccountPageOpenTest(){
        MainPage main = new MainPage(driver);
        main.loginPageOpenWith(LoginWay.MAIN_ACCOUNT);
        AccountPage accountPage = new AccountPage(driver);
        boolean result = accountPage.isPageLoaded();
        Assert.assertTrue(result);
    }
}
