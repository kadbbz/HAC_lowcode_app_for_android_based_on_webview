package com.huozige.lab.container.offlineform.util;

/**
 * OLE Automation Date 工具。
 *
 * <p>ODate 以 1899-12-30 00:00:00 UTC 为基准，用天数（含小数部分）表示时间。</p>
 */
public final class ODateUtils {
    private static final double MILLIS_PER_DAY = 24d * 60d * 60d * 1000d;
    private static final double UNIX_EPOCH_AS_ODATE = 25569d;

    private ODateUtils() {
    }

    public static double now() {
        return fromUnixMillis(System.currentTimeMillis());
    }

    public static double fromUnixMillis(long millis) {
        return UNIX_EPOCH_AS_ODATE + millis / MILLIS_PER_DAY;
    }
}
