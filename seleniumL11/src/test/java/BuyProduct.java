import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BuyProduct {
    private WebDriver driver;
    private WebDriverWait wait;


    @Before
    public void start(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        System.setProperty("webdriver.chrome.driver", "src/main/resources/unix/chromedriver");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 10);
    }


    @Test
    public void buyProductTest() {
        MainPage m = new MainPage();
        for (int buy=0; buy<3; buy++) {
            m.goMainPage(driver);
            m.addProduct(driver,wait);
        }
        BasketPage del = new BasketPage();
        try {
            m.goBasket(driver);
        } catch (TimeoutException ignore) { }
        del.deleteAllFromBasket(driver,wait);
    }


    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
