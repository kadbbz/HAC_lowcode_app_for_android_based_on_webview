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

    HAC.__successCallbacks = new Map();

    HAC.__errorCallbacks = new Map();

    HAC.registryCallback = function(ticket, funcS, funcE){
        var currentWindow = window;
        if(ticket.iFrameName){
            var windows = HAC.getWindowsByFrameName(ticket.iFrameName);
            currentWindow  = windows[0];
        }

        if(!funcE) funcE = function(error){console.log("callback for error was not found. " + error);};
        if(!funcS) funcS = function(payload, payload2){console.log("callback for success was not found. " + payload +" | " + payload2);};

        currentWindow.HAC.__successCallbacks.set(ticket.functionName,funcS);
        currentWindow.HAC.__errorCallbacks.set(ticket.functionName,funcE);
    };

    HAC.dispatchSuccessCallback = function(ticket, payload, payload2){
        var currentWindow = window;
        if(ticket.iFrameName){
            var windows = HAC.getWindowsByFrameName(ticket.iFrameName);
            currentWindow  = windows[0];
        }

        currentWindow.HAC.__successCallbacks.get(ticket.functionName)(payload, payload2);
    };

    HAC.dispatchErrorCallback = function(ticket, error){
        var currentWindow = window;
        if(ticket.iFrameName){
            var windows = HAC.getWindowsByFrameName(ticket.iFrameName);
            currentWindow  = windows[0];
        }

        currentWindow.HAC.__errorCallbacks.get(ticket.functionName)(error);
    };

    console.log("HAC准备就绪。");

    return "版本号：20240509.01";
})();
