// select all nodes
(function() {
    var rootScope = angular.element(document).scope()
    return rootScope.graph.model.cells;
})();



// save graph instance
var graph = angular.element(document).scope().graph;

// SELECT flowline by ADDED NUMBER
graph.setSelectionCell(angular.element(document).scope().graph.model.cells[8]);


// GET SELECTION POINTS (DOM ELEMENT)
document.querySelectorAll('.geDiagramContainer svg > g > g')[2]);

// GET SELECTION POINT AFTER ELEMENT WAS SELECTED
document.querySelectorAll('.geDiagramContainer svg > g > g')[2].querySelector('g[style="cursor: sw-resize; visibility: visible;"]');


//.geDiagramContainer svg > g > g:nth-of-type(2)>g:nth-of-type(1)>path:nth-of-type(3)


//end of path                      #shape-2>path:nth-of-type(3)

//all points of selected box       g>g >rect:nth-of-type(1)


//body > div.ng-scope > div > div > div > div > svg > g > g:nth-child(3) > g:nth-child(2)

//body > div.ng-scope > div > div > div > div > svg > g > g:nth-child(3) > g:nth-child(3)

//body > div.ng-scope > div > div > div > div > svg > g > g:nth-child(3) > g:nth-child(1)

//box rectangular size        var e = document.querySelector('.ng-scope > div > div > div > div > svg > g > g:nth-of-type(3) > g:nth-of-type(1)>rect');     return e.getAttribute('width');