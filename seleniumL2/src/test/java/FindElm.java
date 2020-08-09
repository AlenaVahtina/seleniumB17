import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static settings.IdentifyOS.isUnix;
import static settings.IdentifyOS.isWindows;

public class FindElm {
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
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.get("http://localhost/litecart/admin");
        int numberMenu = driver.findElements(By.cssSelector("li#app-")).size();
        for (int i=0; i<numberMenu; i++){
            driver.findElements(By.cssSelector("li#app-")).get(i).click();
            driver.findElement(By.cssSelector("h1"));
            System.out.println(driver.findElement(By.cssSelector("h1")).getText());
        }
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
