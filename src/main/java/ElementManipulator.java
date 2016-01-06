import addInfo.AbstractPage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by techmagic on 12/15/2015.
 */
public class ElementManipulator {

    public WebDriver driver;
    public Actions action;

    //.ng-binding[ng-bind-html='vm.source.text']
    public String boxPickerXpath = ".//*[@id='tab-content-0']/div/dt-mx-toolbar/md-list/md-list-item[1]";
    public String arrowPickerXpath = ".//*[@id='tab-content-0']/div/dt-mx-toolbar/md-list/md-list-item[2]";
    public String textPickerXpath = ".//*[@id='tab-content-0']/div/dt-mx-toolbar/md-list/md-list-item[3]";

    public String cssPart1 = ".ng-isolate-scope[number='";
    public String cssPart2 = "']";

    //Create resource and drilldown
    public String resourceMakerXpath = "html/body//table/tbody/tr[1]";
    public String drilldownAddDeleteXpath = "html/body//table/tbody/tr[2]";

    //Open editor
    String editViewDiagramBtnCss = ".blue.md-button.md-ink-ripple";
    public String openShapePanelBtnCss = ".md-button[ng-click=\"vm.toggleSidenav('left')\"]";
    public String splitBarCss = ".dt-split-bar";

    //Add drilldown
    String diagramNameContainerXpath = "//body//dt-toolbar/md-toolbar//h3";
    String drilldownLevelTitleXpath = "//body//dt-toolbar/md-toolbar/div[1]/div[3]/span";
    String backFromDrilldownToDiagramCss = ".ng-scope > dt-toolbar > md-toolbar > div.md-toolbar-tools.dt-toolbar-sub > button:nth-of-type(3)";


    public ElementManipulator(WebDriver driver, Actions action){
        this.action = action;
        this.driver = driver;
    }


    public WebElement getElementById(String id){
        return driver.findElement(By.cssSelector("#shape-" + id));
    }

    //RELATED TO BOXES

    //works fine
    public List<WebElement> getSelectedBoxPoints(Box box){
        driver.findElement(By.cssSelector(box.getBoxCss())).click();
        List<WebElement> points = driver.findElements(By.cssSelector("g>g>rect[visibility='hidden']"));
        return points;
    }

    public void resizeBoxByPoint(Box box, WebElement boxPoint, int x, int y){
        driver.findElement(By.cssSelector(box.getBoxCss())).click();


        action.clickAndHold(boxPoint).build().perform();
        action.moveByOffset(x, y).build().perform();

       // action.dragAndDropBy(boxPoint, x, y).build().perform();
       action.release().click().build().perform();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public Box createStartBox(String number){
        WebElement boxPicker = driver.findElement(By.xpath(boxPickerXpath));
        String fullCss = cssPart1+number+cssPart2;
        action.clickAndHold(boxPicker).moveByOffset(350, 350).release().build().perform();
        //action.dragAndDropBy(boxPicker, 300, 300).build().perform();
        return new Box(number);

    }


    //Works fine
    public Box createStartBoxByDoubleClick(String number){
        WebElement boxPicker = driver.findElement(By.xpath(boxPickerXpath));
        action.moveToElement(boxPicker).click().moveByOffset(300, 300).doubleClick().build().perform();
        return new Box(number);

    }

    //Works fine
    public Box getBoxByNumber(String number) {
        return new Box(number);
    }


    //Works fine
    public void addTextToBox(Box box, String text){
        WebElement boxForText = driver.findElement(By.cssSelector(box.getBoxCss()));
        //driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement textField = driver.findElement(By.cssSelector(box.getBoxTextCss()));
        action.doubleClick(boxForText).sendKeys(text).build().perform();
        //action.click(driver.findElement(By.cssSelector(editViewDiagramBtnCss)));
    }


    //Works fine
    public void linkBoxToBox(Box boxFrom, Box boxTo){
        WebElement b1 = driver.findElement(By.cssSelector(boxFrom.getBoxCss()));
        WebElement b2 = driver.findElement(By.cssSelector(boxTo.getBoxCss()));
        b1.click();
        WebElement crossImgOnBox = driver.findElement(By.cssSelector(boxFrom.crossImgOnBoxCss));
        action.clickAndHold(crossImgOnBox).moveToElement(b2).release().click().build().perform();

    }


    //Works fine
    public void moveBox(Box box, int x, int y){
        WebElement b = driver.findElement(By.cssSelector(box.getBoxCss()));
        action.clickAndHold(b).moveByOffset(x, y).release().click().build().perform();

    }


    //Works fine
    public void createResource(Box box, String title){
        WebElement b = driver.findElement(By.cssSelector(box.getBoxCss()));
        //action.click().build().perform();
        action.moveToElement(b).click().build().perform();
        action.contextClick(b).build().perform();

            WebElement resourceMaker = driver.findElement(By.xpath(resourceMakerXpath));
            action.click(resourceMaker).build().perform();

            WebElement textField = driver.findElement(By.cssSelector("md-input-container>input[type='text']"));
            action.click(textField).sendKeys(title).build().perform();

            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            WebElement createResBtn = driver.findElement(By.cssSelector(".md-raised.blue.md-button.md-ink-ripple"));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (createResBtn.isDisplayed()) {
                WebElement createBtn = driver.findElement(By.cssSelector(".md-raised.blue.md-button.md-ink-ripple"));
                action.click(createBtn).build().perform();
            } else {
                WebElement suggestion = driver.findElement(By.cssSelector(".repeated-item.ng-binding.ng-scope:nth-of-type(1)"));
                action.moveToElement(suggestion).click().build().perform();
            }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<WebElement> getBoxResources(Box box){
        //Add exception to handle no resources case
        String resourceCss = ".ng-isolate-scope[number='"+box.number+"'] .freeze-move.ng-binding";
        return (ArrayList) driver.findElements(By.cssSelector(resourceCss));
    }


    //Works fine
    public void moveResourceInBox(Box box, int from, int to){
        ArrayList<WebElement> resources = this.getBoxResources(box);
                 int fromInd = from - 1;
                 int toInd = to - 1;
                 WebElement resFrom = resources.get(fromInd);
                 WebElement resTo = resources.get(toInd);
                 //action.click(resFrom).moveToElement(resTo).moveByOffset(0, 20).release().build().perform();
                 action.dragAndDrop(resFrom, resTo).build().perform();

    }

    public String getTextOfNthResource(Box box, int resourceNum){
        int resourceNumInd = resourceNum - 1;
        ArrayList<WebElement> resources = this.getBoxResources(box);
        return resources.get(resourceNumInd).getText();
    }



    //Works fine
    public void deleteResourceInBox(Box box, int resNumber){
        ArrayList<WebElement> deleteBtns = (ArrayList) driver.findElements(By.cssSelector(".ng-isolate-scope[number='"+box.number+"'] .delete-resource.md-button.md-ink-ripple"));
        int resNumberInd = resNumber - 1;
        if ((deleteBtns.size()<resNumberInd)){
            return;
        }
        else {
            action.moveToElement(this.getBoxResources(box).get(resNumberInd)).moveToElement(deleteBtns.get(resNumberInd)).click().build().perform();
        }

    }

    //Works fine
    public void addDrilldownToBox(Box box){

        WebElement b = driver.findElement(By.cssSelector(box.getBoxCss()));

       // if (this.getBoxDrilldown(box)!=null) {
            action.click(b).contextClick(b).build().perform();
            WebElement drilldownAdder = driver.findElement(By.xpath(drilldownAddDeleteXpath));
            action.click(drilldownAdder).build().perform();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.cssSelector(backFromDrilldownToDiagramCss)).click();

       // }

      //  else{
          //  return;}
    }

    public WebElement getBoxDrilldown(Box box){
        String number = box.number;
        return driver.findElement(By.cssSelector(".ng-isolate-scope[number='"+number+"'] .drilldown.flex.drilldown-on"));
    }

    public void deleteDrilldownFromBox(Box box){

        WebElement b = driver.findElement(By.cssSelector(box.getBoxCss()));


            action.click(b).contextClick(b).build().perform();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            WebElement drilldownAdder = driver.findElement(By.xpath(drilldownAddDeleteXpath));
            action.click(drilldownAdder).build().perform();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }




    //RELATED TO ARROWS

    //Works fine
    public void createArrow(int x, int y) {
        WebElement arrowPicker =  driver.findElement(By.xpath(arrowPickerXpath));
        action.clickAndHold(arrowPicker).moveByOffset(x, y).release().build().perform();

    }


    //RELATED TO TEXT FIELD

    //Works fine
    public void createText(int x, int y){
        WebElement textPicker = driver.findElement(By.xpath(textPickerXpath));
       // action.clickAndHold(textPicker).moveByOffset(x, y).release().click().build().perform();
        action.dragAndDropBy(textPicker, x, y).build().perform();

    }


    public void openEditor(){
        if (!driver.findElement(By.xpath(boxPickerXpath)).isDisplayed()){
            driver.findElement(By.cssSelector(editViewDiagramBtnCss)).click();
            AbstractPage.waitForElementPresent(driver.findElement(By.cssSelector(openShapePanelBtnCss)));
            driver.findElement(By.cssSelector(openShapePanelBtnCss)).click();
        }
        else {
            return;}
    }


}
