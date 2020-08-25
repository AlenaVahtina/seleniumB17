import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static settings.IdentifyOS.isUnix;
import static settings.IdentifyOS.isWindows;

public class Country {
     private WebDriver driver;
     private String mainWindow;
     private String newWindow;
     private Set<String> oldWindows;


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
    public void addCountry() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.xpath("//a[.=' Add New Country']")).click();
        List<WebElement> links= driver.findElements(By.xpath("//i[@class='fa fa-external-link']"));
        for (int i=0;i<links.size();i++){
            mainWindow = driver.getWindowHandle();
            oldWindows = driver.getWindowHandles();
            links.get(i).click();
             newWindow = wait.until((test) -> {
                Set<String> handles = test.getWindowHandles();
                handles.removeAll(oldWindows);
                 return handles.size() > 0 ? handles.iterator().next() : null;
            });
            driver.switchTo().window(newWindow);
            driver.close();
            driver.switchTo().window(mainWindow);
        }
    }


    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
