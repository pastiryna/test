import addInfo.AbstractPage;
import addInfo.Arrow;
import addInfo.ScreenShotMaker;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Listeners(value= ScreenShotMaker.class)
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
        Thread.sleep(15000);

        driver.findElement(By.cssSelector(editDiagramBtnCss)).click();
        abstractPage1.waitForElementPresent(driver.findElement(By.cssSelector(openShapePanelBtnCss)));
        driver.findElement(By.cssSelector(openShapePanelBtnCss)).click();

    }


    @Test
    public void verifyCreateBox() {
        Box box = eleMan.createStartBoxByDoubleClick("1");
        Assert.assertTrue(driver.findElement(By.cssSelector(box.getBoxCss())).isDisplayed(), "Box1 wasn't created!");

    }


    @Test(priority = 1) //id of flowline is needed
    public void verifyLinkBoxesWithFlowline() {
        eleMan.openEditor();
        Box box1 = eleMan.getBoxByNumber("1");
        eleMan.moveBox(box1, 500, 0);
        Box box2 = eleMan.createStartBoxByDoubleClick("2");
        eleMan.linkBoxToBox(box2, box1);

        /*
        JavascriptExecutor js = (JavascriptExecutor) driver;

        //GET id OF LAST CREATED BOX
        driver.findElement(By.cssSelector(box2.getBoxCss())).click();
        String boxId = (String) js.executeScript("var element=document.querySelector('body > div.ng-scope > div > div > div > div > svg > g > g:nth-child(3) > g:nth-child(1)');" +
                "return element.getAttribute('id');");
        //OBTAIN ARROW ID
        String arrowId = Integer.toString(Integer.parseInt(boxId) + 1);
        */

        Assert.assertTrue(driver.findElement(By.cssSelector(box1.getBoxCss())).isDisplayed(), "Box1 isn't shown after linking to Box2!");
        Assert.assertTrue(driver.findElement(By.cssSelector(box2.getBoxCss())).isDisplayed(), "Box2 isn't shown after linking to Box1!");
        Assert.assertTrue(driver.findElement(By.cssSelector(new Arrow("4").getWholePathCss())).isDisplayed(), "Flowline wasn't added!");

        }



    @Test(priority = 2)
    public void changeTextInBox() {
        eleMan.openEditor();
        Box box = eleMan.getBoxByNumber("1");
        Box box2 = eleMan.getBoxByNumber("2");


        eleMan.addTextToBox(box, "Test");
        action.moveToElement(driver.findElement(By.cssSelector(box2.getBoxCss()))).click().build().perform();
        abstractPage1.waitForElementShown();
        String actual = driver.findElement(By.cssSelector(box.getBoxTextCss())).getText();
        Assert.assertEquals(actual, "Test", "Text in box wasn't changed!");


    }

    @Test(priority = 3)
    public void verifyCreateResource() {
        eleMan.openEditor();
        Box box = eleMan.getBoxByNumber("1");
        eleMan.createResource(box, "Resource1");
        abstractPage1.waitForElementShown();
       // WebDriverWait wait = new WebDriverWait(driver, 10);
        ArrayList<WebElement> resource = eleMan.getBoxResources(box);
        Assert.assertTrue(resource.get(0).isDisplayed(), "Resource wasn't created!");

    }


    @Test(priority = 4)
    public void verifyMoveResource() throws InterruptedException {
        eleMan.openEditor();
        box = eleMan.getBoxByNumber("1");
        eleMan.createResource(box, "Resource2");
        abstractPage1.waitForElementShown();
        ArrayList<WebElement> resources1 = eleMan.getBoxResources(box);
        String textOfMovedResource = eleMan.getTextOfNthResource(box, 1);

        eleMan.moveResourceInBox(box, 1, 2);

        Assert.assertEquals(textOfMovedResource, eleMan.getTextOfNthResource(box, 2), "Resource wasn't moved!");


    }


    @Test(priority = 5)
    public void verifyDeleteResource() {
        eleMan.openEditor();
        Box box = eleMan.getBoxByNumber("1");
        ArrayList<WebElement> resources = eleMan.getBoxResources(box);
        int numberOfResourcesBefore = resources.size();
        eleMan.deleteResourceInBox(box, 1);

        Assert.assertNotEquals(eleMan.getBoxResources(box).size(), numberOfResourcesBefore, "Resource wasn't deleted!");

    }




    @Test(priority = 6)   //<--not working
    public void verifyBoxReSize(){
        eleMan.openEditor();
        Box box = eleMan.getBoxByNumber("1");
        driver.findElement(By.cssSelector(box.getBoxCss())).click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        int widthBefore = Integer.parseInt((String)js.executeScript("var e = document.querySelector('.ng-scope > div > div > div > div > svg > g > g:nth-of-type(3) > g:nth-of-type(1)>rect');" +
                "return e.getAttribute('width');"));
        int heightBefore = Integer.parseInt((String) js.executeScript("var e = document.querySelector('.ng-scope > div > div > div > div > svg > g > g:nth-of-type(3) > g:nth-of-type(1)>rect');" +
                "return e.getAttribute('height');"));

        System.out.println("widthBefore= " + widthBefore + " heightBefore= " + heightBefore);

        eleMan.resizeBoxByPoint(box, driver.findElement(By.cssSelector(box.bottomCentrCss)), 0, -300);
        abstractPage1.waitForElementShown();

        int widthAfter = Integer.parseInt((String) js.executeScript("var e = document.querySelector('.ng-scope > div > div > div > div > svg > g > g:nth-of-type(3) > g:nth-of-type(1)>rect');" +
                "return e.getAttribute('width');"));
        int heightAfter = Integer.parseInt((String) js.executeScript("var e = document.querySelector('.ng-scope > div > div > div > div > svg > g > g:nth-of-type(3) > g:nth-of-type(1)>rect');" +
                "return e.getAttribute('height');"));

        Assert.assertEquals(widthAfter, widthBefore, "Width isn't correct!");
        Assert.assertEquals(heightAfter, heightBefore + 300, "Height isn't correct!");

    }



    @Test(priority = 7)  //<---works!!!
    public void verifyFlowlinePointsCoordinates(){
        Arrow arrow = new Arrow("4");
        //Select flowline
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("var graph = angular.element(document).scope().graph;" +
                "graph.setSelectionCell(angular.element(document).scope().graph.model.cells[4]);");

         //Points on graph [2, ---)
        ArrayList<WebElement> all = (ArrayList<WebElement>) driver.findElements(By.cssSelector(".ng-scope > div > div > div > div > svg > g > g:nth-of-type(3) > g"));
        int numberOfItems =  all.size();
        System.out.println("Amount = " + all.size());
        Assert.assertTrue(driver.findElement(By.cssSelector(arrow.getLastPointCssForAttribute(numberOfItems))).isDisplayed(), "Last point wasn't found!");
        WebElement lastPoint = driver.findElement(By.cssSelector(arrow.getLastPointCssForAttribute(numberOfItems)));

        System.out.println("x = "+lastPoint.getAttribute("x"));
        System.out.println("y= " + lastPoint.getAttribute("y"));
        System.out.println("width= " + lastPoint.getAttribute("width"));
        System.out.println("height= " + lastPoint.getAttribute("height"));
    }

    @Test(priority = 8)   //<---works
    public void verifyMoveFlowlineByTargetPoint(){
        Arrow arrow = new Arrow("4");
        //Select flowline
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("var graph = angular.element(document).scope().graph;" +
                "graph.setSelectionCell(angular.element(document).scope().graph.model.cells[4]);");

        WebElement lastPoint = driver.findElement(By.cssSelector(arrow.getLastPointCssForAttribute(driver.findElements(By.cssSelector(".ng-scope > div > div > div > div > svg > g > g:nth-of-type(3) > g")).size())));
        action.clickAndHold(lastPoint).moveByOffset(100, 100).release().click().build().perform();
    }

    @Test(priority = 9)
    public void verifyAddDrilldown() {
        eleMan.openEditor();
        Box box = eleMan.getBoxByNumber("1");
        eleMan.addDrilldownToBox(box);
        eleMan.openEditor();
        Assert.assertTrue(eleMan.getBoxDrilldown(box).isDisplayed(), "Drilldown wasn't added!");

    }

    @Test(priority = 10)
    public void verifyDeleteDrilldown() {
        abstractPage1.waitForElementShown();
        Box box = eleMan.getBoxByNumber("1");
        eleMan.openEditor();
        abstractPage1.waitForElementShown();
        eleMan.deleteDrilldownFromBox(box);
        abstractPage1.waitForElementShown();
        try {
            Assert.assertTrue(driver.findElement(By.cssSelector(".ng-isolate-scope[number='1'].drilldown.flex.drilldown-on")).isDisplayed(), "Drilldown wasn't deleted!");
        }
        catch (NoSuchElementException e){
            Assert.assertTrue(true, "Drilldown wasn't deleted!");
        }

    }


    @Test(priority = 11)
    public void verifyPageRefresh(){
        abstractPage1.refreshPage();
        Assert.assertTrue(eleMan.getElementById("2").isDisplayed(), "Box 1 isn't shown after refreshing the page!");
        Assert.assertTrue(eleMan.getElementById("3").isDisplayed(), "Box 2 isn't shown after refreshing the page!");
        Assert.assertTrue(eleMan.getElementById("4").isDisplayed(), "Flowline isn't shown after refreshing the page!");

    }

    /*

    @Test(priority = 9)   //<--not working
    public void verifyCreateText(){
        eleMan.createText(300, 300);
        Assert.assertTrue(driver.findElement(By.cssSelector(".//*[@id='label-5']/g/foreignobject/div/span")).isDisplayed(), "TextBox isn't created!");

        WebElement textBox = driver.findElement(By.cssSelector(".//*[@id='label-5']/g/foreignobject/div/span"));

        action.moveToElement(textBox).doubleClick().sendKeys("Test Text").build().perform();
        action.moveByOffset(100, 100).click().build().perform();
        String actual = textBox.getText();
        Assert.assertEquals(actual, "Test Text", "Text changes failed!");

    }
    @Test
    public void verifyBoxResize2(){
        Box box = eleMan.getBoxByNumber("2");
        driver.findElement(By.cssSelector(box.getBoxCss())).click();
        WebElement point = driver.findElement(By.cssSelector(box.bottomRightCss));
        action.clickAndHold(point).moveByOffset(100, 100).release().click().build().perform();
    }




    @Test(priority = 11)
    public void changeArrowText(){
        Arrow arrow = new Arrow("5");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("var graph = angular.element(document).scope().graph;" +
                "graph.setSelectionCell(angular.element(document).scope().graph.model.cells[5]);");

        ArrayList<WebElement> arrowPoints = (ArrayList<WebElement>) driver.findElements(By.cssSelector(".ng-scope > div > div > div > div > svg > g > g:nth-of-type(3) > g"));

        WebElement arrowText = driver.findElement(By.cssSelector(arrow.getArrowTextCss(arrowPoints.size())));
        action.doubleClick(arrowText).sendKeys("Test").build().perform();
        action.moveByOffset(100, 100).click().build().perform();

        abstractPage1.waitForElementShown();

        js.executeScript("var graph = angular.element(document).scope().graph;" +
                "graph.setSelectionCell(angular.element(document).scope().graph.model.cells[5]);");
//        action.doubleClick(arrowText).build().perform();
        abstractPage1.waitForElementShown();
        String actual = driver.findElement(By.cssSelector(".ng-scope > div> textarea")).getText();

        Assert.assertEquals(actual, "Test", "Arrow text isn't correct!");

    }
*/

    @AfterTest
    public void afterTest() throws InterruptedException {
        Thread.sleep(5000);
    }






}
