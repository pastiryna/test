package addInfo;

/**
 * Created by techmagic on 12/21/2015.
 */
public class Arrow {
    String id;

    public Arrow(String id){
        this.id = id;

    }

    public String getWholePathCss(){
        return "#shape-"+this.id+">path:nth-of-type(2)";
    }

    public String getTargetPointCss(){
        return "#shape-"+this.id+">path:nth-of-type(3)";
    }

    public String getFirstPointCssForAttribute(){
        return  ".ng-scope > div > div > div > div > svg > g > g:nth-of-type(3) > g:nth-of-type(2)>image";
    }

    public String getLastPointCssForAttribute(int numberOfSelectedItems){

        int indexOfLastPoint = numberOfSelectedItems - 1;
        String indexForReturn = Integer.toString(indexOfLastPoint);
        return ".ng-scope > div > div > div > div > svg > g > g:nth-of-type(3) > g:nth-of-type("+indexForReturn+")>image";
    }


    public String getArrowTextCss(int numberOfSelectedItems){

        int indexOfLastPoint = numberOfSelectedItems;
        String indexForReturn = Integer.toString(indexOfLastPoint);
        return ".ng-scope > div > div > div > div > svg > g > g:nth-of-type(3) > g:nth-of-type("+indexForReturn+")>image";
    }





    //List of all points on path for coordinates taking                  body > div.ng-scope > div > div > div > div > svg > g > g:nth-child(3)
    //Separate points of path  for coordinates taking(starting from 2 index)                    body > div.ng-scope > div > div > div > div > svg > g > g:nth-child(3) > g:nth-child(2)
}
