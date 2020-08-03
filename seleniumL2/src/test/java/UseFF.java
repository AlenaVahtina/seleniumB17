import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static settings.IdentifyOS.isUnix;
import static settings.IdentifyOS.isWindows;

public class UseFF {
    private WebDriver driver;

    @Before
    public void start(){
        if (isUnix()){System.setProperty("webdriver.gecko.driver", "src/main/resources/unix/geckodriver");}
        if (isWindows()){System.setProperty("webdriver.gecko.driver", "src/main/resources/windows/geckodriver.exe");}
        driver =new FirefoxDriver();
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
