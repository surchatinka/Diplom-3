import ru.model.LoginWay;
import ru.model.Token;
import ru.model.User;
import ru.model.WebDriverFactory;
import ru.page.object.LoginPage;
import ru.page.object.MainPage;
import ru.page.object.RegistrationPage;
import ru.client.StellarBurgerClient;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import net.datafaker.Faker;
import net.datafaker.providers.base.Text;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import java.util.Locale;
import static net.datafaker.providers.base.Text.DIGITS;
import static net.datafaker.providers.base.Text.EN_UPPERCASE;
import static ru.model.Endpoints.MAIN_PAGE;

@RunWith(Parameterized.class)
public class RegistrationTest {
    public static final int MIN_PASSWORD_LENGTH = 6;
    private final StellarBurgerClient client = new StellarBurgerClient();
    private final int passwordLength;
    private final boolean result;
    private User user;
    private WebDriver driver;

    public RegistrationTest(int passwordLength,boolean result){
        this.passwordLength=passwordLength;
        this.result = result;
    }

    @Parameterized.Parameters(name = "Password length: {0}; Expected result: {1}")
    public static Object[][] getTestData(){
        return new Object[][]{
                {MIN_PASSWORD_LENGTH,true},
                {MIN_PASSWORD_LENGTH-1,false}
        };
    }

    @DisplayName("Register user test")
    @Description("Test checks if its possible or impossible to make user with premade password length")
    @Test
    public void passwordLengthTest(){
        MainPage mainPage = new MainPage(driver);
        mainPage.loginPageOpenWith(LoginWay.MAIN_PAGE_LOGIN_BUTTON);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.registerLinkClick();
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.registrationSequence(user.getEmail(), user.getPassword(), user.getName());
        boolean ok;
        if (result) {
            LoginPage reopenedPage = new LoginPage(driver);
            ok = reopenedPage.isPageOpened();
        } else {
            ok = registrationPage.isPasswordFieldErrorVisible();
        }
        Assert.assertTrue(ok);
    }

    @Before
    @Step("Test preparation")
    public void before() {
        driver = WebDriverFactory.createWebDriver();
        Faker faker = new Faker(new Locale("en"));
        String email = faker.internet().emailAddress();
        String name = faker.name().firstName();
        String password =  faker.text().text(Text.TextSymbolsBuilder.builder()
                .len(passwordLength)
                .with(EN_UPPERCASE, 2)
                .with(DIGITS, 3).build());
        user = new User(email,password,name);
        driver.get(MAIN_PAGE);
    }

    @After
    @Step("Clean data and shutdown")
    public void after() {
        driver.quit();
        ValidatableResponse response = client.loginUser(user);
        Token token = client.getToken(response);
        if (token.getAccessToken()!=null){
            client.deleteUser(token);
        }
    }
}
