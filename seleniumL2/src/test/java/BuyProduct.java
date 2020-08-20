import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static settings.IdentifyOS.isUnix;
import static settings.IdentifyOS.isWindows;

public class BuyProduct {
    private WebDriver driver;


    @Before
    public void start(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        if (isUnix()){System.setProperty("webdriver.chrome.driver", "src/main/resources/unix/chromedriver");}
        if (isWindows()){System.setProperty("webdriver.chrome.driver", "src/main/resources/windows/chromedriver.exe");}
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
    }


    @Test
    public void buyProductTest() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //Добавление продукта в корзину
        for (int buy=0; buy<3; buy++) {
            driver.get("http://localhost/litecart/en/");
            driver.findElement(By.xpath("//h3[.='Most Popular']/following::div[@class='name']")).click();
            WebElement counts = driver.findElement(By.xpath("//span[@class='quantity']"));
            int count1 = Integer.parseInt(counts.getText());
            try{driver.findElement(By.name("options[Size]")).isDisplayed();
                Select size = new Select(driver.findElement(By.name("options[Size]")));
                size.selectByVisibleText("Small");
                }catch (NoSuchElementException e) {}
            driver.findElement(By.name("add_cart_product")).click();
            wait.until(textToBePresentInElement(counts, String.valueOf(count1+1)));
        }
        //Переход в корзину, если товаров больше 3
        try {
        driver.findElement(By.xpath("//a[.='Checkout »']")).click();
        } catch (TimeoutException ignore) {
        }
        //Удаление по одному элементу из корзины
        wait.until(visibilityOfElementLocated(By.name("remove_cart_item")));
        int countRow = 1;
        while (countRow>0) {
            countRow = driver.findElements(By.xpath("//table[@class='dataTable rounded-corners']/tbody/tr/td[@class='item']")).size();
            WebElement del =wait.until(elementToBeClickable(By.name("remove_cart_item")));
            try{
                del.click();
            }catch (TimeoutException ignore) {
            }
            System.out.println(countRow);
            try {
                wait.until(numberOfElementsToBeLessThan(By.xpath("//table[@class='dataTable rounded-corners']/tbody/tr/td[@class='item']"), countRow));
                countRow = driver.findElements(By.xpath("//table[@class='dataTable rounded-corners']/tbody/tr/td[@class='item']")).size();
                System.out.println(countRow);
            }catch (TimeoutException ignore) {
            }

        }
    }


    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
