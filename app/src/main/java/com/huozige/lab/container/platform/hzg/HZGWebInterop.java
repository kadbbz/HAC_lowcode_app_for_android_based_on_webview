package com.huozige.lab.container.platform.hzg;

import android.webkit.JavascriptInterface;

import com.huozige.lab.container.platform.AbstractWebInterop;
import com.huozige.lab.container.platform.CallbackParams;
import com.huozige.lab.container.utilities.StringConvertUtility;

/**
 * 活字格页面专用
 * 与页面上的单元格进行交互，获取或设置值
 */
public class HZGWebInterop extends AbstractWebInterop {

    /**
     * 设置指定单元格的值
     * @param cellLocation 单元格的位置，如：{"Row":31,"Column":1,"PageID":"p","PageName":"兼容官方APP"}
     * @param rawValue 需要设置的值，统一按照字符串处理
     */
    @Override
    public void setInputValue(String cellLocation, Object rawValue) throws IllegalStateException {

        String scripts = "HAC.setCellValue(" + cellLocation + ",'" + StringConvertUtility.encodeByHAC(rawValue.toString()) + "');";

        executeJavaScript(scripts);
    }

    @Override
    public void callback(String ticket, CallbackParams params) throws IllegalStateException{

        String scripts;
        if(params.isSuccess){
            scripts = "HAC.dispatchSuccessCallback(" + ticket + ",'" + StringConvertUtility.encodeByHAC(params.payload) + "','" + StringConvertUtility.encodeByHAC(params.payload2) + "');";
        }else{
            scripts = "HAC.dispatchErrorCallback(" + ticket + ",'"+  StringConvertUtility.encodeByHAC(params.error) + "');";
        }

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
