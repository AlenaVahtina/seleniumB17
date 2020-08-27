import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static settings.IdentifyOS.isUnix;
import static settings.IdentifyOS.isWindows;

public class Category {
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
    public void сategory() {
        //Вход в систему
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Переход в категорию товаров
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        //Открыть папку Subcategory
        driver.findElement(By.xpath("//a[.='Subcategory']")).click();
        //Получение сколько всего нужных строк в таблице
        int rows = driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr[@class='row']/td[3]/a")).size();
        for (int i=5; i<rows+5; i++){
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.findElement(By.xpath("//table[@class='dataTable']/tbody/tr["+i+"]//td[3]/a")).click();
            driver.manage().logs().get("browser").getAll().forEach(l ->System.out.println(l));
            driver.findElement(By.name("cancel")).click();
        }
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
