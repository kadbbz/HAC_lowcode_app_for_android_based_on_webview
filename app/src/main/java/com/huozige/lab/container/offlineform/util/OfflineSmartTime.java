package com.huozige.lab.container.offlineform.util;

import android.content.Context;

import com.huozige.lab.container.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 智能时间格式化：刚刚 / N分钟前 / N小时前 / 昨天 HH:mm / MM-dd HH:mm / yyyy-MM-dd HH:mm。
 *
 * <p>当天按相对时间展示；跨天用「昨天」，更早用日期。字段更新时间与签名更新时间共用本工具。</p>
 */
public final class OfflineSmartTime {

    private static final long MINUTE_MS = 60_000L;
    private static final long HOUR_MS = 60 * MINUTE_MS;
    private static final String PATTERN_HOUR_MINUTE = "HH:mm";
    private static final String PATTERN_SAME_YEAR = "MM-dd HH:mm";
    private static final String PATTERN_OTHER_YEAR = "yyyy-MM-dd HH:mm";

    private OfflineSmartTime() {
    }

    public static String format(Context context, long unixMillis) {
        return format(context, unixMillis, System.currentTimeMillis());
    }

    /** 空时间返回空串，便于直接 setText。 */
    public static String format(Context context, Long unixMillis) {
        return unixMillis == null ? "" : format(context, unixMillis.longValue());
    }

    /** 按给定“当前时间”格式化，便于测试与批量刷新。 */
    public static String format(Context context, long unixMillis, long nowMillis) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(nowMillis);
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(unixMillis);

        long diff = nowMillis - unixMillis;
        // 轻微的未来时间（时钟误差）按「刚刚」处理，避免出现负数时间。
        if (diff < 0 && diff > -MINUTE_MS) {
            return context.getString(R.string.offline_text_update_time_just_now);
        }
        if (diff >= 0 && isSameDay(now, time)) {
            if (diff < MINUTE_MS) {
                return context.getString(R.string.offline_text_update_time_just_now);
            }
            if (diff < HOUR_MS) {
                return context.getString(R.string.offline_text_update_time_minutes_ago, diff / MINUTE_MS);
            }
            return context.getString(R.string.offline_text_update_time_hours_ago, diff / HOUR_MS);
        }
        if (isYesterday(now, time)) {
            return context.getString(R.string.offline_text_update_time_yesterday,
                    formatDate(time.getTime(), PATTERN_HOUR_MINUTE));
        }
        if (time.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
            return formatDate(time.getTime(), PATTERN_SAME_YEAR);
        }
        return formatDate(time.getTime(), PATTERN_OTHER_YEAR);
    }

    private static boolean isSameDay(Calendar first, Calendar second) {
        return first.get(Calendar.YEAR) == second.get(Calendar.YEAR)
                && first.get(Calendar.DAY_OF_YEAR) == second.get(Calendar.DAY_OF_YEAR);
    }

    private static boolean isYesterday(Calendar now, Calendar time) {
        Calendar yesterday = (Calendar) now.clone();
        yesterday.add(Calendar.DAY_OF_YEAR, -1);
        return isSameDay(yesterday, time);
    }

    private static String formatDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern, Locale.CHINA).format(date);
    }
}
