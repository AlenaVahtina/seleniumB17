import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static settings.IdentifyOS.isUnix;
import static settings.IdentifyOS.isWindows;

public class CountriesTest {

    private WebDriver driver;
    private String country1;
    private String country2;
    private String zone;

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
    public void checkCountry() {
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        for (int i=1; i<driver.findElements(By.cssSelector("tr.row")).size(); i++){
            country1 =driver.findElements(By.cssSelector("tr.row")).get(i).findElement(By.cssSelector("a")).getText();
            country2 =driver.findElements(By.cssSelector("tr.row")).get(i-1).findElement(By.cssSelector("a")).getText();
            if (country1.compareTo(country2)<0)
            {
                System.out.println("Страны не отсортированы");
                return;
            }
            System.out.println(driver.findElements(By.cssSelector("tr.row")).get(i-1).findElements(By.cssSelector("td")).get(5).getText());
            if (!driver.findElements(By.cssSelector("tr.row")).get(i-1).findElements(By.cssSelector("td")).get(5).getText().equals("0")){
                driver.findElements(By.cssSelector("tr.row")).get(i).findElement(By.cssSelector("a")).click();
                System.out.println("В зоне");
                driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
            }
        }
    }

    @Test
    public void checkTimeZone(){
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");

    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
