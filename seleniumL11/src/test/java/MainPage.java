import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

public class MainPage {

    public void goBasket(WebDriver driver){
        driver.findElement(By.xpath("//a[.='Checkout »']")).click();
    }

    public void  goMainPage(WebDriver driver){
        driver.get("http://localhost/litecart/en/");
        driver.findElement(By.xpath("//h3[.='Most Popular']/following::div[@class='name']")).click();
    }

    public void addProduct(WebDriver driver,WebDriverWait wait){
        WebElement counts = driver.findElement(By.xpath("//span[@class='quantity']"));
        int count1 = Integer.parseInt(counts.getText());
        ProductPage product = new ProductPage();
        product.add(driver);
        wait.until(textToBePresentInElement(counts, String.valueOf(count1+1)));
    }
}
