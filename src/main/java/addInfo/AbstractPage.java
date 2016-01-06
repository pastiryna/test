package addInfo;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

/**
 * Created by techmagic on 12/15/2015.
 */
public class AbstractPage {
    public static WebDriver driver;
    //public static WebDriver chromeDriver = new ChromeDriver();
    //String baseUrl;




    public AbstractPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public  void  open(String url){
        driver.manage().window().maximize();
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }


    public static void waitForElementPresent(WebElement element){
        WebElement searchResult = (new WebDriverWait(driver,30))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForPageToLoad(){
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public static void waitForPageToLoadAfterRefresh(){
        try {
            Thread.sleep(25000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitForElementShown(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        }
        catch (NoAlertPresentException e) {
            return false;
        }
    }


    public void refreshPage(){
        driver.navigate().refresh();
        try {
            Thread.sleep(25000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //switch to a newly opened tab/window, close it and back to the old one
    public void switchBetweenTabs(){
        String winHandleBefore = driver.getWindowHandle();

        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        this.waitForPageToLoad();
        String socialUrl = driver.getCurrentUrl();
        driver.close();
        driver.switchTo().window(winHandleBefore);
    }


    //switch to a newly opened tab without any additional actions
    public void switchToOpenedTab(){
        String winHandleBefore = driver.getWindowHandle();

        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        this.waitForPageToLoad();
        String socialUrl = driver.getCurrentUrl();
    }


    /*




    public static void waitForPageToLoadAfterRefresh(){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }


    public static boolean isElementPresent(WebElement element){
        try {
            element.isEnabled();
            System.out.println(element + "is present");
            return true;
        }
        catch (NoSuchElementException e){
            System.out.println(element + "is absent");
            return false;
        }
    }





*/


}
