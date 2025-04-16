import ru.page.object.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import ru.model.BurgerSection;
import ru.model.WebDriverFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import static ru.model.Endpoints.MAIN_PAGE;


@RunWith(Parameterized.class)
public class ConstructorTest {

    private final BurgerSection section;
    private WebDriver driver;

    public ConstructorTest(BurgerSection section){
        this.section=section;
    }

    @Parameterized.Parameters(name = "Testing opening {0} section")
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
        driver.get(MAIN_PAGE);
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
