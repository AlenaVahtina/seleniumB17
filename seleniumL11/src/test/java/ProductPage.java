import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class ProductPage {

    public void add(WebDriver driver){
        try{driver.findElement(By.name("options[Size]")).isDisplayed();
            Select size = new Select(driver.findElement(By.name("options[Size]")));
            size.selectByVisibleText("Small");
        }catch (NoSuchElementException e) {}
        driver.findElement(By.name("add_cart_product")).click();
    }
}
