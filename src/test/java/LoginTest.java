import PageObject.LoginPage;
import PageObject.MainPage;
import PageObject.RegistrationPage;
import PageObject.ResetPasswordPage;
import client.StellarBurgerClient;
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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import java.util.Locale;

@RunWith(Parameterized.class)
public class LoginTest {

    private final LoginWay how;
    private WebDriver driver;
    private User user;
    private Token token;
    private final StellarBurgerClient client = new StellarBurgerClient();

    public LoginTest(LoginWay how){
        this.how = how;
    }

    @Parameterized.Parameters
    public static Object[][] testData(){
        return new Object[][]{
                {LoginWay.MAIN_LOGIN},
                {LoginWay.MAIN_ACCOUNT},
                {LoginWay.REGISTRATION},
                {LoginWay.RESET_PASSWORD}
        };
    }
    @Before
    public void before(){
        driver = WebDriverFactory.createWebDriver();
        Faker faker = new Faker(new Locale("en"));
        user = new User(faker.internet().emailAddress(),faker.bothify("??##??##"),faker.name().firstName());
        ValidatableResponse response = client.createUser(user);
        token = client.getToken(response);
    }
    @After
    public void after(){
        client.deleteUser(token);
        driver.quit();
    }

    @Test
    public void loginThroughTest(){
        openLoginPage(how);
        LoginPage login = new LoginPage(driver);
        login.loginPageSequence(user.getEmail(), user.getPassword());
        MainPage main2 = new MainPage(driver);
        boolean result = main2.isMakeOrderButtonDisplayed();
        Assert.assertTrue("Login fails. Make order button didn't display",result);

    }
    private void openLoginPage(LoginWay how){
        switch (how){
            case MAIN_LOGIN:
            case MAIN_ACCOUNT:
                driver.get("https://stellarburgers.nomoreparties.site/");
                MainPage main = new MainPage(driver);
                main.loginPageOpenWith(how);
                break;
            case REGISTRATION:
                driver.get("https://stellarburgers.nomoreparties.site/register");
                RegistrationPage registrationPage = new RegistrationPage(driver);
                registrationPage.loginLinkClick();
                break;
            case RESET_PASSWORD:
                driver.get("https://stellarburgers.nomoreparties.site/forgot-password");
                ResetPasswordPage resetPasswordPage = new ResetPasswordPage(driver);
                resetPasswordPage.loginLinkClick();
                break;
            default: throw new RuntimeException("No such login method available");
        }
    }
}
