import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;

import static settings.IdentifyOS.isUnix;
import static settings.IdentifyOS.isWindows;

public class CreateUser {

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
    public void stickerTest() {
        driver.get("http://localhost/litecart/en/");
        //Создание нового пользователя
        driver.findElement(By.xpath("//a[.='New customers click here']")).click();
        driver.findElement(By.xpath("//input[@name='company']")).sendKeys("iTest");
        driver.findElement(By.xpath("//input[@name='firstname']")).sendKeys("Samuel");
        driver.findElement(By.xpath("//input[@name='lastname']")).sendKeys("Vimes");
        driver.findElement(By.xpath("//input[@name='address1']")).sendKeys("Ankh-Morkpork");
        driver.findElement(By.xpath("//input[@name='address1']")).sendKeys("Guard Headquarters");
        driver.findElement(By.xpath("//input[@name='postcode']")).sendKeys("1111 1111");
        driver.findElement(By.xpath("//input[@name='city']")).sendKeys("Ankh-Morkpork");
        driver.findElement(By.xpath("//b[@role='presentation']")).click();
        driver.findElement(By.xpath("//li[.='United States']")).click();
        String mail;
        Random random = new Random();
        mail="Ank"+random.nextInt()+"@mail.com";
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys(mail);
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("0000");
        driver.findElement(By.xpath("//input[@name='password']")).clear();
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("test1234");
        driver.findElement(By.xpath("//input[@name='confirmed_password']")).sendKeys("test1234");
        driver.findElement(By.xpath("//button[@name='create_account']")).click();






    }


    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
