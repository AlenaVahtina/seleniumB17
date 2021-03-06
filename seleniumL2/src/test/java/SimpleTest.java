import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static settings.IdentifyOS.*;

public class SimpleTest {

    private WebDriver driver;

    @Before
    public void start(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        if (isUnix()){System.setProperty("webdriver.chrome.driver", "src/main/resources/unix/chromedriver");}
        if (isWindows()){System.setProperty("webdriver.chrome.driver", "src/main/resources/windows/chromedriver.exe");}
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
    }

    @Test
    public void myFirstTest() {
        driver.get("http://ya.ru");
        driver.findElement(By.id("text")).sendKeys("test");
        driver.findElement(By.id("text")).sendKeys(Keys.RETURN);
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }

}
