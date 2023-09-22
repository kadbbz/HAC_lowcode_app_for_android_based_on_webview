(function(){
    window.HAC = {};

    HAC.findWindow = function(w, name, allMatch){
        if(!w || !w.frames){
            return;
        }
        if(w.name === name){
            allMatch.push(w);
        }else{
            for(i = 0; i< w.frames.length; i++){
                HAC.findWindow(w.frames[i], name, allMatch);
            }
        }
    };

    HAC.getWindowsByFrameName = function(name){
        if(!name){
            return [window];
        }else{
            var frames = [];
            HAC.findWindow(window, name, frames);
            return frames;
        }
    };

    HAC.setCellValue = function(cellLocation, value){
        var currentWindow = window;
        if(cellLocation.iFrameName){
            var windows = HAC.getWindowsByFrameName(cellLocation.iFrameName);
            currentWindow  = windows[0];
        }
        currentWindow.Forguncy.Page.getCellByLocation(cellLocation).setValue(value);
    };

    console.log("HAC helper is ready.");

    return "版本号：20230922.01";
})();
