import ru.page.object.AccountPage;
import ru.page.object.MainPage;
import ru.client.StellarBurgerClient;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import ru.model.LoginWay;
import ru.model.Token;
import ru.model.User;
import ru.model.WebDriverFactory;
import net.datafaker.Faker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import java.util.Locale;
import static ru.model.Endpoints.MAIN_PAGE;

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
        driver.get(MAIN_PAGE);
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
        main.loginPageOpenWith(LoginWay.MAIN_PAGE_ACCOUNT_BUTTON);
        AccountPage accountPage = new AccountPage(driver);
        boolean result = accountPage.isPageLoaded();
        Assert.assertTrue(result);
    }
}
