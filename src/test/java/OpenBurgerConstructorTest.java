import PageObject.AccountPage;
import PageObject.MainPage;
import client.StellarBurgerClient;
import io.restassured.response.ValidatableResponse;
import model.*;
import net.datafaker.Faker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Locale;

@RunWith(Parameterized.class)
public class OpenBurgerConstructorTest {
    private Token token;
    private WebDriver driver;
    private final StellarBurgerClient client = new StellarBurgerClient();
    private final ConstructorOpenWay way;

    @Before
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
    public void tearDown() {
        driver.quit();
        if (token.getAccessToken()!=null){
            client.deleteUser(token);
        }
    }

    public OpenBurgerConstructorTest(ConstructorOpenWay way){
        this.way=way;
    }

    @Parameterized.Parameters
    public static Object[][] getValues(){
        return new Object[][]{
                {ConstructorOpenWay.BUTTON},
                {ConstructorOpenWay.LOGO}
        };
    }

    @Test
    public void AccountPageOpenTest(){
        driver.get("https://stellarburgers.nomoreparties.site/account");
        AccountPage accountPage = new AccountPage(driver);
        accountPage.openConstructor(way);
        MainPage reopenedMain = new MainPage(driver);
        boolean result = reopenedMain.isMakeOrderButtonDisplayed() && reopenedMain.isConstructorOpens();
        Assert.assertTrue(result);
    }
}