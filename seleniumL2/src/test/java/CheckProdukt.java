import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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
    private String mainSalePriseColor;
    private String produktPriceColor;
    private String produktSalePriceColor;
    private String mainPriseSize;
    private String mainSalePriseSize;
    private String mainSalePriseBold;
    private String produktSalePriseBold;


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
        //Определение порядка уток в 1 блоке "Most Popular" при первом входе на страницу
        WebElement block1 = driver.findElement(By.cssSelector("div#box-most-popular"));
        ArrayList<String> ducks = new ArrayList<String>();
        for (int i=0;i<block1.findElements(By.xpath(".//div[@class='name']")).size();i++){
            ducks.add(block1.findElements(By.xpath(".//div[@class='name']")).get(i).getText());
        }
        System.out.println(ducks);
        //Проверка всех уток в зависимости от списка в первом заходе
        for (int j=0; j<ducks.size();j++){
            mainName=ducks.get(j);
            //Проверка на наличия стикера скидка, потому что если его нет, нет скидочной цены, а основная не перечеркнута
            if (!block1.findElements(By.xpath(".//div[.='"+mainName+"']/preceding-sibling::div[@class='image-wrapper']/div[@class='sticker sale']")).isEmpty()){
                mainPrise=block1.findElement(By.xpath(".//div[.='"+mainName+"']/following::div[@class='price-wrapper']//s[@class='regular-price']")).getText();
                //Вывод описание утки для проверки
                System.out.println("Утака "+mainName+" цена "+mainPrise+" цена по скидке "+mainSalePrise);
                //Проверка цвета первоночальной цены на главной странице
                mainPriseColor=block1.findElement(By.xpath(".//div[.='"+mainName+"']/following::div[@class='price-wrapper']//s[@class='regular-price']")).getCssValue("color");
                String[] color=mainPriseColor.split(",");
                if(!(color[0].substring(5).equals(color[1].substring(1)))&&(color[1].substring(1).equals(color[2].substring(1)))){
                    System.out.println("Цена не серая");
                }
                //Проверка, что первоночальная цена перечеркнута на главной странице
                if(!block1.findElement(By.xpath(".//div[.='"+mainName+"']/following::div[@class='price-wrapper']//s[@class='regular-price']")).getCssValue("text-decoration").split(" ")[0].equals("line-through")){
                    System.out.println("Первоночальная цена не перечеркнута");
                }
                //Проверка, что скидочная цена на главной странице красная
                mainSalePriseColor=block1.findElement(By.xpath(".//div[.='"+mainName+"']/following::div[@class='price-wrapper']//strong[@class='campaign-price']")).getCssValue("color");
                String[] colorSale=mainSalePriseColor.split(",");
                if (!(colorSale[1].substring(1).equals(colorSale[2].substring(1)))&&(colorSale[2].substring(1).equals("0"))){
                    System.out.println("Скидочная цена на главной странице не красная");
                }
                //Проверка, что скидочная цена на главной странице полужирная
                mainSalePriseBold=block1.findElement(By.xpath(".//div[.='"+mainName+"']/following::div[@class='price-wrapper']//strong[@class='campaign-price']")).getCssValue("font-weight");
                if (Integer.parseInt(mainSalePriseBold)<400){
                    System.out.println("Скидочная цена на странице товара не выделена жирным");
                }
                //Проверка, что скидочная цена меньше первоночальной по наминалу
                mainSalePrise=block1.findElement(By.xpath(".//div[.='"+mainName+"']/following::div[@class='price-wrapper']//strong[@class='campaign-price']")).getText();
                if(Integer.parseInt(mainPrise.substring(1))<Integer.parseInt(mainSalePrise.substring(1))){
                    System.out.println("На главной странице скидочная цена больше обычной");
                }
                //Проверка, что скидочная цена меньше первоночальной по размеру на основной странице
                mainSalePriseSize=block1.findElement(By.xpath(".//div[.='"+mainName+"']/following::div[@class='price-wrapper']//strong[@class='campaign-price']")).getCssValue("font-size");
                mainPriseSize=block1.findElement(By.xpath(".//div[.='"+mainName+"']/following::div[@class='price-wrapper']//s[@class='regular-price']")).getCssValue("font-size");
                if (Float.parseFloat(mainSalePriseSize.toString().split("px")[0])<Float.parseFloat(mainPriseSize.split("px")[0])){
                    System.out.println("Скидочная цена на главной странице меньше основной");
                }
            }
            else {
                mainPrise=block1.findElement(By.xpath(".//div[.='"+mainName+"']/following::div[@class='price-wrapper']//span[@class='price']")).getText();
                //Вывод описание утки для проверки
                System.out.println("Утака "+mainName+" цена "+mainPrise+" цена по скидке "+mainSalePrise);
                mainPriseColor=block1.findElement(By.xpath(".//div[.='"+mainName+"']/following::div[@class='price-wrapper']//span[@class='price']")).getCssValue("color");
                String[] color=mainPriseColor.split(",");
                if(!(color[0].substring(5).equals(color[1].substring(1)))&&(color[1].substring(1).equals(color[2].substring(1)))){
                    System.out.println("Цена не серая");
                }


            }
            //Переход на страницу товара
            block1.findElement(By.xpath(".//div[.='"+mainName+"']")).click();
            //Проверка, что названия совпадают
            produktName=driver.findElement(By.cssSelector("h1.title")).getText();
            if (!mainName.equals(produktName)){
                System.out.println("Название товара на главной странице и на странице товара не совпадает");
            }
            //Проверка, что цены совпадают
            if (driver.findElements(By.xpath("//div[@class='content']/div[@class='images-wrapper']//div[@class='sticker sale']")).isEmpty()){
                produktPrice=driver.findElement(By.xpath("//div/span[@class='price']")).getText();
                if (!mainPrise.equals(produktPrice)) {
                    System.out.println("Цена на главной странице и на странице товара не совпадает");
                }
                //Проверка основная цена на странице продукта серая
                produktPriceColor=driver.findElement(By.xpath("//div/span[@class='price']")).getCssValue("color");
                String[] color2=produktPriceColor.split(",");
                if(!(color2[0].substring(5).equals(color2[1].substring(1)))&&(color2[1].substring(1).equals(color2[2].substring(1)))){
                    System.out.println("Цена не серая");
                }
            }
            else {
                produktPrice=driver.findElement(By.xpath("//div/s[@class='regular-price']")).getText();
                produktSalePrice=driver.findElement(By.xpath("//div/strong[@class='campaign-price']")).getText();
                if(Integer.parseInt(produktPrice.substring(1))<Integer.parseInt(produktSalePrice.substring(1))){
                    System.out.println("На странице продукта скидочная цена больше обычной");
                }
                //Сравнение скидочных цен на главной странице и странице товара
                if(!mainSalePrise.equals(produktSalePrice)){
                    System.out.println("Скидочная цена на главной странице и на странице товара не совпадает");
                }
                //Сравнение цен на главной странице и странице товара
                if(!mainPrise.equals(produktPrice)){
                    System.out.println("Цена на главной странице и на странице товара не совпадает");
                }
                //Проверка, что основная цена серая
                produktPriceColor=driver.findElement(By.xpath("//div/s[@class='regular-price']")).getCssValue("color");
                String[] color2=produktPriceColor.split(",");
                if(!(color2[0].substring(5).equals(color2[1].substring(1)))&&(color2[1].substring(1).equals(color2[2].substring(1)))){
                    System.out.println("Цена не серая");
                }
                //Проверка, что основная цена перечеркнута
                if(!driver.findElement(By.xpath("//div/s[@class='regular-price']")).getCssValue("text-decoration").split(" ")[0].equals("line-through")){
                    System.out.println("Первоночальная цена не перечеркнута");
                }
                //Проверка, что скидочная цена красная
                produktSalePriceColor=driver.findElement(By.xpath("//div/strong[@class='campaign-price']")).getCssValue("color");
                String[] colorSale2=produktSalePriceColor.split(",");
                if (!(colorSale2[1].substring(1).equals(colorSale2[2].substring(1)))&&(colorSale2[2].substring(1).equals("0"))) {
                    System.out.println("Скидочная цена на странице товара не красная");
                }
                //Проверка, что скидочная цена на странице товара полужирная
                produktSalePriseBold=driver.findElement(By.xpath("//div/strong[@class='campaign-price']")).getCssValue("font-weight");
                if (Integer.parseInt(mainSalePriseBold)<400){
                    System.out.println("Скидочная цена на главной странице не выделена жирным");
                }
                //Проверка, что скидочная цена меньше первоночальной по размеру на странице продукта
                mainPriseSize=driver.findElement(By.xpath("//div/s[@class='regular-price']")).getCssValue("font-size");
                mainSalePriseSize=driver.findElement(By.xpath("//div/strong[@class='campaign-price']")).getCssValue("font-size");
                if (Float.parseFloat(mainSalePriseSize.toString().split("px")[0])<Float.parseFloat(mainPriseSize.split("px")[0])){
                    System.out.println("Скидочная цена на главной странице меньше основной");
                }
            }
            //Возвращение на основную страницу и обнуление переменных
            driver.get("http://localhost/litecart/en/");
            block1 = driver.findElement(By.cssSelector("div#box-most-popular"));
            mainPrise=null;
            mainName=null;
            mainSalePrise=null;
            produktName=null;
            produktPrice=null;
            produktSalePrice=null;
            mainSalePriseSize=null;
            mainPriseSize=null;
        }
    }


    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
