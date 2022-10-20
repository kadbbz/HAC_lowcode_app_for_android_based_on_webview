package com.huozige.lab.container.proxy;

import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.hjq.permissions.Permission;

import locationprovider.davidserrano.com.LocationProvider;

/**
 * 让页面能获取当前的地理位置
 * geo.getLocation(coordinateSystem,cellLat,cellLon,cellError)：获取地理位置
 */
public class GeoProxy extends AbstractProxy {

    static final String CS_WGS84 = "wgs84";
    static final String CS_BD09 = "bd09";

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

        // 获取地理位置，需要先申请权限
        getInterop().requirePermission(new String[]{
                Permission.ACCESS_FINE_LOCATION,
                Permission.ACCESS_COARSE_LOCATION
        }, () -> {
            //create a callback
            LocationProvider.LocationCallback callback = new LocationProvider.LocationCallback() {

                private void returnWithWGS84(float lat, float lon) {

                    if (CS_BD09.equalsIgnoreCase(coordinateSystem)) {
                        // 优先百度坐标，可配套百度地图使用
                        double[] bd09 = Geo_CoordinateSystemHelpers.wgs84_bd09(lat, lon);
                        getInterop().setInputValue(cellLat, String.valueOf(bd09[0]));
                        getInterop().setInputValue(cellLon, String.valueOf(bd09[1]));
                    } else if (CS_WGS84.equalsIgnoreCase(coordinateSystem)) {
                        // 然后是GPS坐标
                        getInterop().setInputValue(cellLat, String.valueOf(lat));
                        getInterop().setInputValue(cellLon, String.valueOf(lon));
                    } else {
                        // 默认为国内火星坐标
                        double[] gcj02 = Geo_CoordinateSystemHelpers.wgs84_gcj02(lat, lon);
                        getInterop().setInputValue(cellLat, String.valueOf(gcj02[0]));
                        getInterop().setInputValue(cellLon, String.valueOf(gcj02[1]));
                    }

                    // 重置错误信息
                    getInterop().setInputValue(cellErr, "");
                }

                @Override
                public void onNewLocationAvailable(float lat, float lon) {

                    getInterop().writeLogIntoConsole("getLocation NewLocationAvailable : lat " + lat + " lon " + lon + " (WGS84).");
                    returnWithWGS84(lat, lon);
                }

                @Override
                public void locationServicesNotEnabled() {
                    getInterop().writeErrorIntoConsole("getLocation Location Services Not Enabled");
                    getInterop().setInputValue(cellErr, "LocationServicesNotEnabled");
                }

                @Override
                public void updateLocationInBackground(float lat, float lon) {
                    //if a listener returns after the main locationAvailable callback, it will go here
                    getInterop().writeLogIntoConsole("getLocation UpdateLocationInBackground : lat " + lat + " lon " + lon + " (WGS84).");

                    returnWithWGS84(lat, lon);
                }

                @Override
                public void networkListenerInitialised() {
                    //when the library switched from GPS only to GPS & network
                    getInterop().writeLogIntoConsole("getLocation Network Listener Initialised");
                }

                @Override
                public void locationRequestStopped() {
                    getInterop().writeLogIntoConsole("getLocation location Request Stopped");
                }
            };

            //initialise an instance with the two required parameters
            LocationProvider provider = new LocationProvider.Builder()
                    .setContext(getInterop().getActivityContext())
                    .setListener(callback)
                    .create();

            //start getting location
            provider.requestLocation();
        });
    }

    @Override
    public String getName() {
        return "geo";
    }

    /**
     * 转换坐标体系
     */
    static class Geo_CoordinateSystemHelpers {

        /**
         * 圆周率PI
         */
        private static final double PI = Math.PI;
        /**
         * 卫星椭球坐标投影到平面地图坐标系的投影因子
         */
        private static final double AXIS = 6378245.0;
        /**
         * 椭球的偏心率(a^2 - b^2) / a^2
         */
        private static final double OFFSET = 0.00669342162296594323;
        /**
         * 圆周率转换量
         */
        private static final double X_PI = PI * 3000.0 / 180.0;

        /**
         * <p>
         * gcj02_bd09方法主要用于-GCJ02坐标转换为百度09坐标.
         * </p>
         * <p>
         * 城邑耕夫 2016-11-21 - 下午6:06:44
         * </p>
         *
         * @param lat gcj02维度
         * @param lng gcj02经度
         * @return latlng[] 经纬数组（bd09）
         */
        public static double[] gcj02_bd09(double lat, double lng) {
            double[] latlng = new double[2];
            double z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * X_PI);
            double theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * X_PI);
            latlng[0] = z * Math.sin(theta) + 0.006;
            latlng[1] = z * Math.cos(theta) + 0.0065;
            return latlng;
        }

        /**
         * <p>
         * wgs84_bd09方法主要用于-wgs84地球坐标换转为百度09坐标.
         * <br>转换过程：wgs84->gcj02->bd09.
         * </p>
         * <p>
         * 城邑耕夫 2016-11-21 - 下午6:29:08
         * </p>
         *
         * @param lat 维度（wgs84）
         * @param lng 经度（wgs84）
         * @return latlng[] 维经数组（bd09）
         */
        public static double[] wgs84_bd09(double lat, double lng) {
            double[] latlon = wgs84_gcj02(lat, lng);
            return gcj02_bd09(latlon[0], latlon[1]);
        }

        /**
         * <p>
         * wgs84_gcj02方法主要用于-wgs84地球坐标转换为gcj02.
         * </p>
         * <p>
         * 城邑耕夫 2016-11-21 - 下午6:29:45
         * </p>
         *
         * @param lat 维度（wgs84）
         * @param lng 经度（wgs84）
         * @return latlng[] 维经数组（gcj-02）
         */
        public static double[] wgs84_gcj02(double lat, double lng) {
            double[] latlon = new double[2];
            if (outOfChina(lat, lng)) {
                latlon[0] = lat;
                latlon[1] = lng;
                return latlon;
            }
            double[] deltaD = transform(lat, lng);
            latlon[0] = lat + deltaD[0];
            latlon[1] = lng + deltaD[1];
            return latlon;
        }


        /**
         * <p>
         * transform方法主要用于-wgs84与gcj02的坐标转换.
         * </p>
         * <p>
         * 山河戀夢 2016-11-21 - 下午8:06:56
         * </p>
         *
         * @param lat 维度
         * @param lng 经度
         * @return double[] 两坐标系间的偏移
         */
        public static double[] transform(double lat, double lng) {
            double[] latlng = new double[2];
            double dLat = transformLat(lng - 105.0, lat - 35.0);
            double dLon = transformLng(lng - 105.0, lat - 35.0);
            double radLat = lat / 180.0 * PI;
            double magic = Math.sin(radLat);
            magic = 1 - OFFSET * magic * magic;
            double sqrtMagic = Math.sqrt(magic);
            dLat = (dLat * 180.0) / ((AXIS * (1 - OFFSET)) / (magic * sqrtMagic) * PI);
            dLon = (dLon * 180.0) / (AXIS / sqrtMagic * Math.cos(radLat) * PI);
            latlng[0] = dLat;
            latlng[1] = dLon;
            return latlng;
        }

        public static double transformLat(double x, double y) {
            double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
            ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
            ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
            ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
            return ret;
        }

        public static double transformLng(double x, double y) {
            double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
            ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
            ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
            ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
            return ret;
        }

        public static boolean outOfChina(double lat, double lon) {
            if (lon < 72.004 || lon > 137.8347) {
                return true;
            }
            return lat < 0.8293 || lat > 55.8271;
        }

    }
}
