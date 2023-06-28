package com.huozige.lab.container.platform.hzg;

import android.webkit.JavascriptInterface;

import com.huozige.lab.container.platform.AbstractWebInterop;

/**
 * 活字格页面专用
 * 与页面上的单元格进行交互，获取或设置值
 */
public class HZGWebInterop extends AbstractWebInterop {

    private String buildScriptOfCurrentWindow(String cellLocation){
        String scripts = "var HAC_getWindowsByFrameName = function(name){\n" +
                "\tif(!name){\n" +
                "\t\treturn [window];\n" +
                "\t}else{\n" +
                "\t\tvar frames = [];\t\t\t\t\n" +
                "\t\tHAC_FindWindow(window, name, frames);\n" +
                "\t\treturn frames;\n" +
                "\t}\n" +
                "};\n" +
                "\n" +
                "var HAC_FindWindow = function(w, name, allMatch){\n" +
                "\tif(!w || !w.frames){\n" +
                "\t\treturn;\n" +
                "\t}\n" +
                "\tif(w.name === name){\n" +
                "\t\tallMatch.push(w);\n" +
                "\t}else{\n" +
                "\t\tfor(i = 0; i< w.frames.length; i++){\n" +
                "\t\t\tHAC_FindWindow(w.frames[i], name, allMatch);\n" +
                "\t\t}\n" +
                "\t}\n" +
                "};";

        scripts+="var cellLocation= ";
        scripts+=cellLocation;
        scripts+=";\n" +
                "var currentWindow = window;\n" +
                "if(cellLocation.iFrameName){\n" +
                "\tvar windows = HAC_getWindowsByFrameName(cellLocation.iFrameName);\n" +
                "\tcurrentWindow  = windows[0]; \n" +
                "}\n";
        return  scripts;
    }

    /**
     * 设置指定单元格的值
     * @param cellLocation 单元格的位置，如：{"Row":31,"Column":1,"PageID":"p","PageName":"兼容官方APP"}
     * @param rawValue 需要设置的值，统一按照字符串处理
     */
    @Override
    public void setInputValue(String cellLocation, Object rawValue) throws IllegalStateException {

        // 直接转换为字符串
        rawValue = rawValue.toString();

        String scripts = buildScriptOfCurrentWindow(cellLocation);
        scripts+="currentWindow.Forguncy.Page.getCellByLocation(" + cellLocation + ").setValue('" +rawValue + "');";

        executeJavaScript(scripts);
    }


    /**
     * 用于从HTML中传回字符串值的JS代理
     */
    static class ValueBundleProxy {

        public String Value;

        /**
         * 将JS的值传递到Java中。
         * @param value 需要传回的值
         */
        @JavascriptInterface
        public void setValue(String value){
            this.Value = value;
        }
    }

}
