package com.huozige.lab.container.offlineform.formitem.select;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.formitem.picker.WheelHaptics;
import com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper;
import com.zyyoona7.wheel.WheelView;

import java.util.List;

/**
 * 选择框的底部抽屉滚轮：替代原来的单选项弹窗，与日期/时间滚轮同一套外观与交互。
 *
 * <p>标题栏左侧「取消」、中间字段标题、右侧「确定」；点「确定」才把选中位置回传给调用方，
 * 取消或下滑关闭视为未选择。滚轮逐项滚动带触感反馈。</p>
 */
public final class OfflineSelectWheelSheet {

    /** 用户在滚轮中确认某选项时回调，position 为选项在列表中的位置。 */
    public interface OnOptionConfirmedListener {
        void onOptionConfirmed(int position);
    }

    /** 滚轮可见条目数（奇数保证中间行居中）。 */
    private static final int VISIBLE_ITEMS = 7;
    private static final float WHEEL_TEXT_SIZE_SP = 20f;
    private static final float LINE_SPACING_DP = 10f;
    /** 抽屉高度占视窗高度的比例。 */
    private static final float SHEET_HEIGHT_RATIO = 0.42f;
    /** 视窗过矮时的兜底高度（dp）。 */
    private static final int MIN_SHEET_HEIGHT_DP = 260;

    private final Context context;
    private final String title;
    private final List<String> labels;
    private final int initialPosition;
    private final OnOptionConfirmedListener listener;

    private BottomSheetDialog dialog;
    private WheelView<String> optionWheel;

    private OfflineSelectWheelSheet(Context context, String title, List<String> labels,
                                    int initialPosition, OnOptionConfirmedListener listener) {
        this.context = context;
        this.title = title;
        this.labels = labels;
        this.initialPosition = initialPosition;
        this.listener = listener;
    }

    /**
     * 打开选择滚轮抽屉。
     *
     * @param labels          选项显示文案
     * @param initialPosition 当前选中位置；无选中传 -1
     */
    public static void show(Context context, String title, List<String> labels,
                            int initialPosition, OnOptionConfirmedListener listener) {
        if (labels == null || labels.isEmpty()) {
            return;
        }
        new OfflineSelectWheelSheet(context, title, labels, initialPosition, listener).show();
    }

    private void show() {
        dialog = new BottomSheetDialog(context);
        View content = LayoutInflater.from(context).inflate(R.layout.offline_form_wheel_option_sheet, null);
        applySheetHeight(content);

        TextView titleView = content.findViewById(R.id.option_wheel_title);
        TextView cancelView = content.findViewById(R.id.option_wheel_cancel);
        TextView confirmView = content.findViewById(R.id.option_wheel_confirm);
        optionWheel = content.findViewById(R.id.wheel_option);

        titleView.setText(title == null ? "" : title);
        configureWheel(optionWheel);
        optionWheel.setData(labels);

        int safePosition = Math.max(0, Math.min(initialPosition, labels.size() - 1));
        optionWheel.setSelectedItemPosition(safePosition, false);

        // 初始位置设定之后再挂载，避免程序化定位被当成用户滚动而误振。
        WheelHaptics haptics = new WheelHaptics(context);
        optionWheel.setOnWheelChangedListener(new WheelView.OnWheelChangedListener() {
            @Override
            public void onWheelScroll(int scrollOffsetY) {
            }

            @Override
            public void onWheelItemChanged(int oldPosition, int newPosition) {
                haptics.tick();
            }

            @Override
            public void onWheelSelected(int position) {
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
            }
        });

        cancelView.setOnClickListener(v -> dialog.dismiss());
        confirmView.setOnClickListener(v -> {
            int position = optionWheel.getSelectedItemPosition();
            dialog.dismiss();
            if (listener != null && position >= 0 && position < labels.size()) {
                listener.onOptionConfirmed(position);
            }
        });

        dialog.setContentView(content);
        dialog.show();

        // 滚轮自身需要消费纵向拖动，禁止抽屉跟手拖动，否则手势会被 BottomSheetBehavior 抢走。
        BottomSheetBehavior<FrameLayout> behavior = dialog.getBehavior();
        behavior.setDraggable(false);
        behavior.setFitToContents(true);
    }

    /** 抽屉高度固定为视窗的 42%（不低于兜底高度），滚轮用 weight 吃掉剩余空间。 */
    private void applySheetHeight(View content) {
        int viewportHeight = context.getResources().getDisplayMetrics().heightPixels;
        int targetHeight = Math.max(
                Math.round(viewportHeight * SHEET_HEIGHT_RATIO),
                OfflineFormUiUnitHelper.dp(context, MIN_SHEET_HEIGHT_DP));
        content.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, targetHeight));
    }

    /** 与日期/时间滚轮一致的外观：选中行深色、其余浅色，细线分隔，柔和 3D 弧面。 */
    private void configureWheel(WheelView<String> wheel) {
        wheel.setVisibleItems(VISIBLE_ITEMS);
        wheel.setCurved(true);
        wheel.setCurvedArcDirection(WheelView.CURVED_ARC_DIRECTION_CENTER);
        wheel.setTextSize(WHEEL_TEXT_SIZE_SP, true);
        wheel.setLineSpacing(LINE_SPACING_DP, true);
        wheel.setTextAlign(WheelView.TEXT_ALIGN_CENTER);
        wheel.setNormalItemTextColor(ContextCompat.getColor(context, R.color.offline_form_muted));
        wheel.setSelectedItemTextColor(ContextCompat.getColor(context, R.color.offline_form_ink));
        wheel.setShowDivider(true);
        wheel.setDividerType(WheelView.DIVIDER_TYPE_WRAP);
        wheel.setDividerColor(ContextCompat.getColor(context, R.color.offline_form_line_strong));
        wheel.setDividerHeight(OfflineFormUiUnitHelper.dp(context, 1));
        wheel.setDividerPaddingForWrap(OfflineFormUiUnitHelper.dp(context, 24));
        wheel.setCyclic(false);
    }
}
