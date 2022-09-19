package com.huozige.lab.container.Geo;

public class CoordinateSystemHelpers {

    /**圆周率PI */
    private static final double PI = Math.PI;
    /**卫星椭球坐标投影到平面地图坐标系的投影因子*/
    private static final double AXIS = 6378245.0;
    /**椭球的偏心率(a^2 - b^2) / a^2 */
    private static final double OFFSET = 0.00669342162296594323;
    /**圆周率转换量*/
    private static final double X_PI = PI * 3000.0 / 180.0;

    /**
     * <p>
     * gcj02_bd09方法主要用于-GCJ02坐标转换为百度09坐标.
     * </p>
     * <p>
     * 城邑耕夫 2016-11-21 - 下午6:06:44
     * </p>
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
