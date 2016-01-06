import org.openqa.selenium.WebElement;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by techmagic on 12/21/2015.
 */
public class Activity {

    String id;

    String textCss; // "#label-"+"id"+" .ng-binding[ng-bind-html='vm.source.text']";

    String resourcesListCss; // "#label-"+"id"+"md-list-item";

    public String crossImgOnBoxCss= ".geDiagramContainer>img"; //<--- is shown only in case a box is selected

    public WebElement topLeft= null;
    public WebElement topCentr = null;
    public WebElement topRight = null;
    public WebElement left = null;
    public WebElement right = null;
    public WebElement bottomLeft = null;
    public WebElement bottomCentr = null;
    public WebElement bottomRight =  null;

    public Activity(String id){
        this.id = id;
        this.textCss = "#label-"+id+" .ng-binding[ng-bind-html='vm.source.text']";
        this.resourcesListCss = "#label-"+id+"md-list-item";

    }

    public void setActivityPoints(ArrayList<WebElement> points){
        this.topLeft = points.get(0);
        this.topCentr = points.get(1);
        this.topRight = points.get(2);
        this.left = points.get(3);
        this.right = points.get(4);
        this.bottomLeft = points.get(5);
        this.bottomCentr = points.get(6);
        this.bottomRight = points.get(7);
    }
}
