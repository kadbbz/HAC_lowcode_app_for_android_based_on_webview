package com.huozige.lab.container.offlineform.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;

/**
 * 底部抽屉内容高度统一策略（人员签名抽屉 / 分组目录抽屉共用）：
 * 先按内容自然高度测量，超过视窗比例上限才固定到上限，并让列表在抽屉内部滚动。
 *
 * <p>约定抽屉内容根布局为竖直 {@link LinearLayout}：顶部拖拽条 + 标题行 + 发丝线 + 可滚动列表，
 * 列表是最后一个子 View。测量完成后列表改为 0dp + weight=1，在固定高度的抽屉里吃掉剩余空间。</p>
 */
public final class OfflineFormSheetHeight {

    /** 抽屉最高占视窗高度比例，超过后列表内部滚动。 */
    private static final float MAX_HEIGHT_RATIO = 0.78f;

    private OfflineFormSheetHeight() {
    }

    /** 按统一策略量高并显示抽屉。 */
    public static void showWithMeasuredHeight(Context context, BottomSheetDialog dialog,
                                              View content, View scrollView) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int maxHeight = Math.round(metrics.heightPixels * MAX_HEIGHT_RATIO);

        ViewGroup.LayoutParams scrollParams = scrollView.getLayoutParams();
        boolean weighted = scrollParams instanceof LinearLayout.LayoutParams;
        if (weighted) {
            ((LinearLayout.LayoutParams) scrollParams).weight = 0;
        }
        scrollParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        scrollView.setLayoutParams(scrollParams);

        content.measure(
                View.MeasureSpec.makeMeasureSpec(screenWidth, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(maxHeight, View.MeasureSpec.AT_MOST));
        int height = Math.min(content.getMeasuredHeight(), maxHeight);

        if (weighted) {
            ((LinearLayout.LayoutParams) scrollParams).weight = 1;
        }
        scrollParams.height = 0;
        scrollView.setLayoutParams(scrollParams);

        content.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        dialog.setContentView(content);
        dialog.show();
    }
}
