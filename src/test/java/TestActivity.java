import addInfo.AbstractPage;
import addInfo.ScreenShotMaker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * Created by techmagic on 12/21/2015.
 */
public class TestActivity {


    @Listeners(value = ScreenShotMaker.class)
/**
 * Created by techmagic on 12/15/2015.
 */
    public class Tests {

        WebDriver driver;
        WebElement emailField;
        WebElement passwordField;
        WebElement signInBtn;
        WebElement editBtn;
        AbstractPage abstractPage1, abstractPage2;
        WebElement openShapePanel;
        Actions action;
        ElementManipulator eleMan;

        Box box = null;

        String editDiagramBtnCss = ".blue.md-button.md-ink-ripple";
        public String openShapePanelBtnCss = ".md-button[ng-click=\"vm.toggleSidenav('left')\"]";
        public String splitBarCss = ".dt-split-bar";

        //DON"T CHANGE xpathes BELOW!!!
        public String boxPickerXpath = ".//*[@id='tab-content-0']/div/dt-mx-toolbar/md-list/md-list-item[1]";
        public String arrowPickerXpath = ".//*[@id='tab-content-0']/div/dt-mx-toolbar/md-list/md-list-item[2]";


        @BeforeSuite
        public void openBrowser() throws InterruptedException {

            System.setProperty("webdriver.chrome.driver", "C:/Users/chromedriver_win32/chromedriver.exe");
            driver = new ChromeDriver();

            driver.get("http://qa.q9elements.com/");
            driver.manage().window().maximize();

            action = new Actions(driver);
            eleMan = new ElementManipulator(driver, action);
            abstractPage1 = new AbstractPage(driver);

            abstractPage1.waitForPageToLoad();
            emailField = driver.findElement(By.name("email"));
            emailField.clear();
            emailField.sendKeys("iryna.pastukhova@techmagic.co");

            passwordField = driver.findElement(By.name("password"));
            passwordField.clear();
            passwordField.sendKeys("03123781QA");

            signInBtn = driver.findElement(By.cssSelector(".btn.btn-login"));
            signInBtn.click();


            abstractPage1.waitForPageToLoad();

            WebElement testLink = driver.findElement(By.xpath("//span/a[contains(text(),'Test1')]"));
            abstractPage1.waitForElementPresent(testLink);
            testLink.click();


            abstractPage1.switchToOpenedTab();
            Thread.sleep(25000);

            driver.findElement(By.cssSelector(editDiagramBtnCss)).click();
            abstractPage1.waitForElementPresent(driver.findElement(By.cssSelector(openShapePanelBtnCss)));
            driver.findElement(By.cssSelector(openShapePanelBtnCss)).click();

        }

    }

    //@Test

}
