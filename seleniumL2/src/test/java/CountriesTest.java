import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static settings.IdentifyOS.isUnix;
import static settings.IdentifyOS.isWindows;

public class CountriesTest {

    private WebDriver driver;
    private String country1;
    private String country2;
    private int zone;
    private String countTimezone;
    private String timeZone1;
    private String timeZone2;
    private String geoZones;

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
    public void checkCountry() {
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        for (int i=1; i<driver.findElements(By.cssSelector("tr.row")).size(); i++){
            country1 =driver.findElements(By.cssSelector("tr.row")).get(i).findElement(By.cssSelector("a")).getText();
            country2 =driver.findElements(By.cssSelector("tr.row")).get(i-1).findElement(By.cssSelector("a")).getText();
            if (country1.compareTo(country2)<0)
            {
                System.out.println("Страны не отсортированы");
                return;
            }
            countTimezone = driver.findElements(By.cssSelector("tr.row")).get(i).findElements(By.cssSelector("td")).get(5).getText();
            if (!countTimezone.equals("0")){
                driver.findElements(By.cssSelector("tr.row")).get(i).findElement(By.cssSelector("a")).click();
                for (int j=2; j<Integer.parseInt(countTimezone); j++){
                    timeZone1 =driver.findElement(By.xpath("//table[@id='table-zones']/tbody/tr["+j+"]/td[3]")).getText();
                    int j2=j+1;
                    timeZone2 =driver.findElement(By.xpath("//table[@id='table-zones']/tbody/tr["+j2+"]/td[3]")).getText();
                    if (timeZone1.compareTo(timeZone2)>0)
                    {
                        System.out.println("Часовые пояса не отсортированы");
                        return;
                    }
                }
                driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
            }
        }
    }

    @Test
    public void checkTimeZone(){
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        geoZones = driver.findElement(By.cssSelector("tr.footer td")).getText().split(" ")[2];
        for (int i=2; i<2+Integer.parseInt(geoZones); i++){
            if(Integer.parseInt(driver.findElement(By.xpath("//table[@class='dataTable']/tbody//tr["+i+"]/td[4]")).getText())>0){
                driver.findElement(By.xpath("//table[@class='dataTable']/tbody//tr["+i+"]/td[3]/a")).click();
                driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                for (int j=0; j<driver.findElements(By.xpath("//table[@class='dataTable']//td[1]")).size()-2;j++){
                    timeZone1=driver.findElements(By.xpath("//table[@class='dataTable']//td[3]/select/option[@selected='selected']")).get(j).getText();
                    timeZone2=driver.findElements(By.xpath("//table[@class='dataTable']//td[3]/select/option[@selected='selected']")).get(j+1).getText();
                    if (timeZone1.compareTo(timeZone2)>0)
                    {
                        System.out.println("Зоны не отсортированы");
                        return;
                    }
                }
                driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
            }
        }

    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
        timeZone1=null;
        timeZone2=null;
    }
}
