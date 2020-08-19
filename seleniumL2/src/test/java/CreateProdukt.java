import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static settings.IdentifyOS.isUnix;
import static settings.IdentifyOS.isWindows;

public class CreateProdukt {

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
    public void createProdukt() {
        //Вход в систему
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        //Переход в Catalog
//        driver.findElement(By.xpath("//span[.='Catalog']")).click();
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog");
        //Переход в создание продукта
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElements(By.xpath("//a[@class='button']")).get(1).click();
        //Заполнение вкладки General
        driver.findElement(By.name("name[en]")).sendKeys("Duck unicorn");
        driver.findElement(By.name("code")).sendKeys("du123");
        driver.findElement(By.xpath("//input[@data-name='Rubber Ducks']")).click();
        System.out.println(driver.findElement(By.xpath("//input[@data-name='Rubber Ducks']")).isSelected());
        driver.findElement(By.xpath("//td[.='Unisex']/preceding::input[@name='product_groups[]']")).click();
        driver.findElement(By.name("quantity")).sendKeys("10");
        WebElement uploadFile = driver.findElement(By.xpath("//input[@type='file']"));
        File file = new File("du.jpeg");
        System.out.println(file.getAbsolutePath().split("du.jpeg")[0]+"src/test/java/du.jpeg");
        uploadFile.sendKeys(file.getAbsolutePath().split("du.jpeg")[0]+"src/test/java/du.jpeg");
        driver.findElement(By.name("date_valid_from")).sendKeys("10102020");
        driver.findElement(By.name("date_valid_to")).sendKeys("10102022");

    }


//    @After
//    public void stop(){
//        driver.quit();
//        driver = null;
//    }
}
