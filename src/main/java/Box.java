import org.openqa.selenium.WebElement;

import java.util.ArrayList;


/**
 * Created by techmagic on 12/15/2015.
 */
public class Box {

    public String id;

    public String number;
    public String cssPart1 = ".ng-isolate-scope[number='";
    public String cssPart2 = "']";

    public String textInBoxCssPart1 = ".ng-isolate-scope[number='";
    public String textInBoxCssPart2 = "'] .ng-binding[ng-bind-html='vm.source.text']";


    public String crossImgOnBoxCss= ".geDiagramContainer>img"; //<--- is shown only in case a box is selected

    //SHOWN WHILE BOX IS SELECTED
    public String boxRectForJS = "";
    public String boxRectForResizeCss = ".ng-scope > div > div > div > div > svg > g > g:nth-of-type(3) > g:nth-of-type(1)";
    public String topLeftCss= ".ng-scope > div > div > div > div > svg > g > g:nth-of-type(3) > g:nth-of-type(2)>image";
    public String topCentrCss =".ng-scope > div > div > div > div > svg > g > g:nth-of-type(3) > g:nth-of-type(3)>image";
    public String topRightCss = ".ng-scope > div > div > div > div > svg > g > g:nth-of-type(3) > g:nth-of-type(4)>image";
    public String leftCss = ".ng-scope > div > div > div > div > svg > g > g:nth-of-type(3) > g:nth-of-type(5)>image";
    public String rightCss = ".ng-scope > div > div > div > div > svg > g > g:nth-of-type(3) > g:nth-of-type(6)>image";
    public String bottomLeftCss = ".ng-scope > div > div > div > div > svg > g > g:nth-of-type(3) > g:nth-of-type(7)>image";
    public String bottomCentrCss = ".ng-scope > div > div > div > div > svg > g > g:nth-of-type(3) > g:nth-of-type(8)>image";
    public String bottomRightCss =  ".ng-scope > div > div > div > div > svg > g > g:nth-of-type(3) > g:nth-of-type(9)>image";


    public Box(String number){
        this.number = number;
    }

   // public String getBoxId()

    public String getBoxCss(){
        return cssPart1+this.number+cssPart2;
    }

    public String getBoxTextCss(){
        return textInBoxCssPart1+number+textInBoxCssPart2;
    }

    public void setBoxId(String id){
        this.id = "#label-"+id;
    }

    public String getBoxId(){
        return this.id;
    }








   //    .ng-isolate-scope[number='1']

// resources of current box          .ng-isolate-scope[number='1'] .freeze-move.ng-binding

}
