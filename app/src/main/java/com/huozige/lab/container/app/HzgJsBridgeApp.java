package com.huozige.lab.container.app;

import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.huozige.lab.container.BaseBridge;
import com.huozige.lab.container.HzgWebInteropHelpers;
import com.huozige.lab.container.R;

/**
 * 让页面具备对APP壳子的感知能力
 * app.getVersion(cell)：获取版本号
 * app.getPackageName(cell)：获取入口的包名
 */
public class HzgJsBridgeApp extends BaseBridge {

    String _versionCell, _packageCell; // 单元格位置缓存

    /**
     * 基础的构造函数
     *
     * @param context 上下文
     * @param webView 浏览器内核
     */
    public HzgJsBridgeApp(AppCompatActivity context, WebView webView) {
        super(context, webView);
    }

    /**
     * 注册的名称为：app
     */
    @Override
    public String GetName() {
        return "app";
    }

    /**
     * 无需操作
     */
    @Override
    public void OnActivityCreated() {

    }

    @Override
    public void BeforeActivityDestroy() {

    }

    @Override
    public void BeforeActivityPause() {

    }

    @Override
    public void OnActivityResumed() {

    }

    /**
     * 无需操作
     *
     * @param requestCode 同onActivityResult
     * @param resultCode  同onActivityResult
     * @param data        同onActivityResult
     * @return 跳过这个JS桥，处理下一个
     */
    @Override
    public Boolean ProcessActivityResult(int requestCode, int resultCode, Intent data) {
        return false;
    }

    /**
     * 注册到页面的app.getPackageName(cell)方法
     * 获取APP入口的包名
     */
    @JavascriptInterface
    public void getPackageName(String cell) {

        // 记录参数
        _packageCell = cell;

        // 需要调度回主线程操作
        ActivityContext.runOnUiThread(() -> HzgWebInteropHelpers.WriteStringValueIntoCell(CurrentWebView, _packageCell, ActivityContext.getPackageName()));
    }

    /**
     * 注册到页面的app.getVersion(cell)方法
     * 获取版本号
     */
    @JavascriptInterface
    public void getVersion(String cell) {

        // 记录参数
        _versionCell = cell;

        // 需要调度回主线程操作
        ActivityContext.runOnUiThread(() -> HzgWebInteropHelpers.WriteStringValueIntoCell(CurrentWebView, _versionCell, ActivityContext.getString(R.string.app_version_name)));
    }
}
