import PageObject.MainPage;
import client.StellarBurgerClient;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import model.BurgerSection;
import model.WebDriverFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;


@RunWith(Parameterized.class)
public class ConstructorTest {

    private final BurgerSection section;
    private WebDriver driver;
    private final StellarBurgerClient client = new StellarBurgerClient();

    public ConstructorTest(BurgerSection section){
        this.section=section;
    }

    @Parameterized.Parameters
    public static Object[][] parameters(){
        return new Object[][]{
                {BurgerSection.BUN},
                {BurgerSection.SAUCE},
                {BurgerSection.INGREDIENTS}
        };
    }

    @Before
    @Step("Test preparation")
    public void before() {
        driver = WebDriverFactory.createWebDriver();
        driver.get("https://stellarburgers.nomoreparties.site");
    }

    @After
    @Step("Clean data and shutdown")
    public void after() {
        driver.quit();
    }

    @Test
    @DisplayName("Open burger constructor section")
    @Description("Test check if constructor section centered")
    public void openSectionTest(){
        MainPage main = new MainPage(driver);
        boolean result = main.isElementInViewport(section);
        Assert.assertTrue(result);
    }
}
