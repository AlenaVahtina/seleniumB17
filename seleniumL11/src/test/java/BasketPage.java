import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBeLessThan;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class BasketPage {

    public void deleteAllFromBasket(WebDriver driver, WebDriverWait wait){
        wait.until(visibilityOfElementLocated(By.name("remove_cart_item")));
        int countRow = 1;
        while (countRow>0) {
            countRow = driver.findElements(By.xpath("//table[@class='dataTable rounded-corners']/tbody/tr/td[@class='item']")).size();
            WebElement del =wait.until(elementToBeClickable(By.name("remove_cart_item")));
            try{
                del.click();
            }catch (TimeoutException ignore) {
            }
            try {
                wait.until(numberOfElementsToBeLessThan(By.xpath("//table[@class='dataTable rounded-corners']/tbody/tr/td[@class='item']"), countRow));
                countRow = driver.findElements(By.xpath("//table[@class='dataTable rounded-corners']/tbody/tr/td[@class='item']")).size();
            }catch (TimeoutException ignore) {
            }

        }
    }

    public void deleteOneProductFromBasket(WebDriver driver, WebDriverWait wait) {
        WebElement del = wait.until(elementToBeClickable(By.name("remove_cart_item")));
        try {
            del.click();
        } catch (TimeoutException ignore) {
        }
        try {
            wait.until(numberOfElementsToBeLessThan(By.xpath("//table[@class='dataTable rounded-corners']/tbody/tr/td[@class='item']"), 1));
        } catch (TimeoutException ignore) {
        }
    }
}
