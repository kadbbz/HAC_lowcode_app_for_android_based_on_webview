package com.huozige.lab.container.offlineform.util;

import android.content.Context;

/**
 * 离线填报动态 UI 使用的尺寸转换工具。
 */
public final class OfflineFormUiUnitHelper {
    private OfflineFormUiUnitHelper() {
    }

    /**
     * 将 dp 转换成当前屏幕密度下的 px。
     */
    public static int dp(Context context, int value) {
        return (int) (value * context.getResources().getDisplayMetrics().density + 0.5f);
    }

}
