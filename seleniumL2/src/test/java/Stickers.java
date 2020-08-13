import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static settings.IdentifyOS.isUnix;
import static settings.IdentifyOS.isWindows;

public class Stickers {

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
        int productCount = driver.findElements(By.cssSelector("[class^=product]")).size();
        System.out.println("Количестово уток "+productCount);
        int stickerCount= driver.findElements(By.cssSelector("[class^=sticker]")).size();
        System.out.println("Количестово стикеров "+stickerCount);
        if(productCount==stickerCount){
            System.out.println("У каждого продукта есть стикер");
        }
        else {
            System.out.println("Проверьте, что у каждого товара есть стикер");
        }

    }


    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
