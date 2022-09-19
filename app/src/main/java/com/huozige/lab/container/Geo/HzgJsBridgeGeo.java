package com.huozige.lab.container.Geo;

import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;


import com.hjq.permissions.Permission;
import com.huozige.lab.container.BaseBridge;
import com.huozige.lab.container.HACBaseActivity;
import com.huozige.lab.container.HzgWebInteropHelpers;
import com.huozige.lab.container.PermissionHelpers;


import locationprovider.davidserrano.com.LocationProvider;

/**
 * 让页面能获取当前的地理位置
 * geo.getLocation(coordinateSystem,cellLat,cellLon,cellError)：获取地理位置
 */
public class HzgJsBridgeGeo extends BaseBridge {

    static final  String CS_WGS84 = "wgs84";
    static final  String CS_BD09 = "bd09";

    /**
     * 基础的构造函数
     *
     * @param context 上下文
     * @param webView 浏览器内核
     */
    public HzgJsBridgeGeo(HACBaseActivity context, WebView webView) {
        super(context, webView);
    }

    /**
     * 注册到页面的geo.getPackageName(cell)方法
     * 将当前地址信息填写到单元格
     * 支持的坐标系（coordinateSystem）：
     * WGS84坐标系：即地球坐标系，是为GPS全球定位系统使用而建立的坐标系统，国际上通用的坐标系。如Google Earth（不含中国）
     * GCJ02坐标系：又称火星坐标系，中国国家测绘局制订的地理信息系统的坐标系统，WGS84坐标系经加密（加入随机的偏差）后的坐标系。如Google中国、搜搜、阿里云、高德
     * BD09坐标系：即百度坐标系，在GCJ02坐标系再次加密后的坐标系。如百度
      */
    @JavascriptInterface
    public void getLocation(String coordinateSystem, String cellLat, String cellLon, String cellErr) {

        PermissionHelpers.RequirePermission(ActivityContext, Permission.ACCESS_FINE_LOCATION);
        PermissionHelpers.RequirePermission(ActivityContext, Permission.ACCESS_COARSE_LOCATION);

        //create a callback
        LocationProvider.LocationCallback callback = new LocationProvider.LocationCallback() {

            private void returnWithWGS84(float lat, float lon) {

                if(CS_BD09.equalsIgnoreCase(coordinateSystem)){
                    // 优先百度坐标，可配套百度地图使用
                    double[] bd09= CoordinateSystemHelpers.wgs84_bd09(lat,lon);
                    HzgWebInteropHelpers.WriteStringValueIntoCell(CurrentWebView, cellLat, String.valueOf( bd09[0]));
                    HzgWebInteropHelpers.WriteStringValueIntoCell(CurrentWebView, cellLon, String.valueOf( bd09[1]));
                }else if(CS_WGS84.equalsIgnoreCase(coordinateSystem)){
                    // 然后是GPS坐标
                    HzgWebInteropHelpers.WriteStringValueIntoCell(CurrentWebView, cellLat, String.valueOf( lat));
                    HzgWebInteropHelpers.WriteStringValueIntoCell(CurrentWebView, cellLon, String.valueOf( lon));
                }else{
                    // 默认为国内火星坐标
                    double[] gcj02= CoordinateSystemHelpers.wgs84_gcj02(lat,lon);
                    HzgWebInteropHelpers.WriteStringValueIntoCell(CurrentWebView, cellLat, String.valueOf( gcj02[0]));
                    HzgWebInteropHelpers.WriteStringValueIntoCell(CurrentWebView, cellLon, String.valueOf( gcj02[1]));
                }

                // 重置错误信息
                HzgWebInteropHelpers.WriteStringValueIntoCell(CurrentWebView, cellErr, "");
            }

            @Override
            public void onNewLocationAvailable(float lat, float lon) {

                HzgWebInteropHelpers.WriteLogIntoConsole(CurrentWebView, "getLocation NewLocationAvailable : lat " + lat + " lon " + lon +" (WGS84).");
                returnWithWGS84(lat,lon);
            }

            @Override
            public void locationServicesNotEnabled() {
                HzgWebInteropHelpers.WriteErrorIntoConsole(CurrentWebView,"getLocation Location Services Not Enabled");
                HzgWebInteropHelpers.WriteStringValueIntoCell(CurrentWebView, cellErr, "LocationServicesNotEnabled");
            }

            @Override
            public void updateLocationInBackground(float lat, float lon) {
                //if a listener returns after the main locationAvailable callback, it will go here
                HzgWebInteropHelpers.WriteLogIntoConsole(CurrentWebView, "getLocation UpdateLocationInBackground : lat " + lat + " lon " + lon +" (WGS84).");

                returnWithWGS84(lat,lon);
            }

            @Override
            public void networkListenerInitialised() {
                //when the library switched from GPS only to GPS & network
                HzgWebInteropHelpers.WriteLogIntoConsole(CurrentWebView,"getLocation Network Listener Initialised");
            }

            @Override
            public void locationRequestStopped() {
                HzgWebInteropHelpers.WriteLogIntoConsole(CurrentWebView,"getLocation location Request Stopped");
            }
        };

        //initialise an instance with the two required parameters
        LocationProvider provider = new LocationProvider.Builder()
                .setContext(ActivityContext)
                .setListener(callback)
                .create();

        //start getting location
        provider.requestLocation();

    }

    @Override
    public String GetName() {
        return "geo";
    }

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

    @Override
    public Boolean ProcessActivityResult(int requestCode, int resultCode, Intent data) {
        return false;
    }
}
