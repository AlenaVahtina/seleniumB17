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
    private String produktName;
    private String produktPrice;
    private String produktSalePrice;
    private String mainPriseColor;


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
            if (!block1.findElements(By.xpath(".//div[.='"+mainName+"']/preceding-sibling::div[@class='image-wrapper']/div[@class='sticker sale']")).isEmpty()){
                mainPrise=block1.findElement(By.xpath(".//div[.='"+mainName+"']/following::div[@class='price-wrapper']//s[@class='regular-price']")).getText();
                mainPriseColor=block1.findElement(By.xpath(".//div[.='"+mainName+"']/following::div[@class='price-wrapper']//s[@class='regular-price']")).getCssValue("color");
                String[] color=mainPriseColor.split(",");
                if(!(color[0].substring(5).equals(color[1].substring(1)))&&(color[1].substring(1).equals(color[2].substring(1)))){
                    System.out.println("Цена не серая");
                }
                mainSalePrise=block1.findElement(By.xpath(".//div[.='"+mainName+"']/following::div[@class='price-wrapper']//strong[@class='campaign-price']")).getText();
                if(Integer.parseInt(mainPrise.substring(1))<Integer.parseInt(mainSalePrise.substring(1))){
                    System.out.println("На главной странице скидочная цена больше обычной");
                }
            }
            else {
                mainPrise=block1.findElement(By.xpath(".//div[.='"+mainName+"']/following::div[@class='price-wrapper']//span[@class='price']")).getText();
                mainPriseColor=block1.findElement(By.xpath(".//div[.='"+mainName+"']/following::div[@class='price-wrapper']//span[@class='price']")).getCssValue("color");
                String[] color=mainPriseColor.split(",");
                if(!(color[0].substring(5).equals(color[1].substring(1)))&&(color[1].substring(1).equals(color[2].substring(1)))){
                    System.out.println("Цена не серая");
                }


            }
            block1.findElement(By.xpath(".//div[.='"+mainName+"']")).click();
            produktName=driver.findElement(By.cssSelector("h1.title")).getText();
            if (!mainName.equals(produktName)){
                System.out.println("Название товара на главной странице и на странице товара не совпадает");
            }
            if (!driver.findElements(By.xpath("//div[@class='content']/div[@class='images-wrapper']//div[@class='sticker sale']")).isEmpty()){
                produktPrice=driver.findElement(By.xpath("//div/span[@class='price']")).getText();
                if (!mainPrise.equals(produktPrice)){
                    System.out.println("Цена на главной странице и на странице товара не совпадает");
                }
                else {
                    produktPrice=driver.findElement(By.xpath("//div/s[@class='regular-price']")).getText();
                    produktSalePrice=driver.findElement(By.xpath("//div/strong[@class='campaign-price']")).getText();
                    if(Integer.parseInt(produktPrice.substring(1))<Integer.parseInt(produktSalePrice.substring(1))){
                        System.out.println("На странице продукта скидочная цена больше обычной");
                    }
                    if(!mainSalePrise.equals(produktSalePrice)){
                        System.out.println("Скидочная цена на главной странице и на странице товара не совпадает");
                    }
                    if(!mainPrise.equals(produktPrice)){
                        System.out.println("Цена на главной странице и на странице товара не совпадает");
                    }
                }
            }
            driver.get("http://localhost/litecart/en/");
            block1 = driver.findElement(By.cssSelector("div#box-most-popular"));
            System.out.println("Утака "+mainName+" цена "+mainPrise+" цена по скидке "+mainSalePrise);
            mainPrise=null;
            mainName=null;
            mainSalePrise=null;
            produktName=null;
            produktPrice=null;
            produktSalePrice=null;
        }
    }


    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
