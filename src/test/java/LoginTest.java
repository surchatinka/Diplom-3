import ru.page.object.LoginPage;
import ru.page.object.MainPage;
import ru.page.object.RegistrationPage;
import ru.page.object.ResetPasswordPage;
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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import java.util.Locale;
import static ru.model.Endpoints.*;

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

    @Parameterized.Parameters(name = "Login through {0}")
    public static Object[][] testData(){
        return new Object[][]{
                {LoginWay.MAIN_PAGE_LOGIN_BUTTON},
                {LoginWay.MAIN_PAGE_ACCOUNT_BUTTON},
                {LoginWay.REGISTRATION_PAGE_LOGIN_LINK},
                {LoginWay.RESET_PASSWORD_PAGE_LOGIN_LINK}
        };
    }
    @Before
    @Step("Test preparation")
    public void before(){
        driver = WebDriverFactory.createWebDriver();
        Faker faker = new Faker(new Locale("en"));
        user = new User(faker.internet().emailAddress(),faker.bothify("??##??##"),faker.name().firstName());
        ValidatableResponse response = client.createUser(user);
        token = client.getToken(response);
    }
    @After
    @Step("Clean data and shutdown")
    public void after(){
        client.deleteUser(token);
        driver.quit();
    }

    @Test
    @DisplayName("Login test")
    @Description("Test checks transition to login page and is login works fine")
    public void loginThroughTest(){
        openLoginPage(how);
        LoginPage login = new LoginPage(driver);
        login.loginPageSequence(user.getEmail(), user.getPassword());
        MainPage main2 = new MainPage(driver);
        boolean result = main2.isMakeOrderButtonDisplayed();
        Assert.assertTrue("Login fails. Make order button didn't display",result);

    }
    @Step("Open login page via selected method")
    private void openLoginPage(LoginWay how){
        switch (how){
            case MAIN_PAGE_LOGIN_BUTTON:
            case MAIN_PAGE_ACCOUNT_BUTTON:
                driver.get(MAIN_PAGE);
                MainPage main = new MainPage(driver);
                main.loginPageOpenWith(how);
                break;
            case REGISTRATION_PAGE_LOGIN_LINK:
                driver.get(REGISTRATION_PAGE);
                RegistrationPage registrationPage = new RegistrationPage(driver);
                registrationPage.loginLinkClick();
                break;
            case RESET_PASSWORD_PAGE_LOGIN_LINK:
                driver.get(RESTORE_PASSWORD_PAGE);
                ResetPasswordPage resetPasswordPage = new ResetPasswordPage(driver);
                resetPasswordPage.loginLinkClick();
                break;
            default: throw new RuntimeException("No such login method available");
        }
    }
}
