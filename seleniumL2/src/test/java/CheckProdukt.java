import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;

import static settings.IdentifyOS.isUnix;
import static settings.IdentifyOS.isWindows;

public class CheckProdukt {
    private WebDriver driver;
    private String mainName;
    private String mainPrise;
    private String mainSalePrise;


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
    public void CheckProduktTest() {
        driver.get("http://localhost/litecart/en/");
        WebElement block1 = driver.findElement(By.cssSelector("div#box-most-popular"));
        ArrayList<String> ducks = new ArrayList<String>();
        for (int i=0;i<block1.findElements(By.xpath(".//div[@class='name']")).size();i++){
            ducks.add(block1.findElements(By.xpath(".//div[@class='name']")).get(i).getText());
        }
        System.out.println(ducks);
        for (int j=0; j<ducks.size();j++){
            mainName=ducks.get(j);
            if (block1.findElement(By.xpath("//div[.='"+mainName+"']/preceding::div[@class='sticker sale']")).isDisplayed()){
                mainPrise=block1.findElement(By.xpath(".//div[.='"+mainName+"']/following::div[@class='price-wrapper']//s[@class='regular-price']")).getText();
                mainSalePrise=block1.findElement(By.xpath(".//div[.='"+mainName+"']/following::div[@class='price-wrapper']//strong[@class='campaign-price']")).getText();
            }
            else {
                mainPrise=block1.findElement(By.xpath(".//div[.='"+mainName+"']/following::div[@class='price-wrapper']//span[@class='price']")).getText();
            }
            block1.findElement(By.xpath("//div[.='"+mainName+"']")).click();
            driver.get("http://localhost/litecart/en/");
            System.out.println("Утака "+mainName+" цена "+mainPrise);
        }
    }


    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
