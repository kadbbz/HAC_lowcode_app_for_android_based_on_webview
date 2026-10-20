package com.huozige.lab.container.offlineform.formitem.picker;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper;
import com.zyyoona7.wheel.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * 从底部弹出的滚轮式日期/时间选择抽屉，用于替代系统自带的 DatePickerDialog、TimePickerDialog。
 *
 * <p>交互与主流移动端一致：滚轮在抽屉内滚动，点「确定」才提交，「取消」或点击遮罩放弃修改。
 * 日期与时间共用同一行滚轮，按模式隐藏不需要的列：
 * <ul>
 *     <li>仅日期：年 / 月 / 日</li>
 *     <li>日期 + 时间：年 / 月 / 日 / 时 / 分 / 秒，单行 6 列</li>
 *     <li>仅时间：时 / 分（可选秒）</li>
 * </ul>
 *
 * <p>单行 6 列时每列只有约 56dp，塞不下「2026年」这类带单位的长文案，因此滚轮只显示数字，
 * 单位信息由标题下方的实时预览承担，滚动时同步刷新。
 *
 * <p>提交的值格式与改造前保持一致，避免影响表单 JSON 契约：
 * {@code yyyy/M/d}、{@code yyyy/M/d HH:mm:ss}、{@code HH:mm}、{@code HH:mm:ss}。
 */
public final class OfflineWheelPickerSheet {

    /** 值确认回调，仅在用户点「确定」时触发。 */
    public interface OnValueConfirmedListener {
        void onValueConfirmed(String value);
    }

    private static final int MIN_YEAR = 1900;
    private static final int MAX_YEAR = 2100;

    /**
     * 滚轮可见条目数，取奇数以保证中间行严格居中。
     * <p>抽屉高度固定后滚轮行用 weight 铺满，实际显示行数由高度决定；该值只在测量退化为
     * wrap_content 时兜底，仍按 {@code itemHeight * visibleItems * 2 / π} 估算高度。
     */
    private static final int VISIBLE_ITEMS = 13;

    /** 行间距（dp），让相邻选项之间留出呼吸感。 */
    private static final float LINE_SPACING_DP = 4f;

    /** 抽屉高度占视窗高度的比例。 */
    private static final float SHEET_HEIGHT_RATIO = 0.45f;

    /** 视窗过矮时的兜底高度（dp），保证滚轮至少留出几行可滚动区域。 */
    private static final int MIN_SHEET_HEIGHT_DP = 280;

    private final Context context;
    private final Calendar initial;
    private final boolean dateMode;
    private final boolean includeTime;
    private final boolean includeSeconds;
    private final OnValueConfirmedListener listener;

    private WheelView<String> yearWheel;
    private WheelView<String> monthWheel;
    private WheelView<String> dayWheel;
    private WheelView<String> hourWheel;
    private WheelView<String> minuteWheel;
    private WheelView<String> secondWheel;
    private TextView previewView;
    private TextView todayShortcut;
    private WheelHaptics haptics;

    /** 重建日期列数据时置位，避免联动回调自我递归，也避免程序化改值误触振动。 */
    private boolean rebuildingDays;

    private OfflineWheelPickerSheet(
            Context context,
            Calendar initial,
            boolean dateMode,
            boolean includeTime,
            boolean includeSeconds,
            OnValueConfirmedListener listener) {
        this.context = context;
        this.initial = initial == null ? Calendar.getInstance() : (Calendar) initial.clone();
        this.dateMode = dateMode;
        this.includeTime = includeTime;
        this.includeSeconds = includeSeconds;
        this.listener = listener;
    }

    /**
     * 打开日期选择抽屉。
     *
     * @param initial     初始时间（已从当前值解析，空值时传当前时间）
     * @param includeTime 是否同时选择时分秒
     */
    public static void showDate(Context context, Calendar initial, boolean includeTime, OnValueConfirmedListener listener) {
        new OfflineWheelPickerSheet(context, initial, true, includeTime, false, listener).show();
    }

    /**
     * 打开时间选择抽屉。
     *
     * @param includeSeconds 是否精确到秒
     */
    public static void showTime(Context context, Calendar initial, boolean includeSeconds, OnValueConfirmedListener listener) {
        new OfflineWheelPickerSheet(context, initial, false, false, includeSeconds, listener).show();
    }

    private void show() {
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View content = LayoutInflater.from(context).inflate(R.layout.offline_form_wheel_picker_sheet, null);
        applySheetHeight(content);

        TextView titleView = content.findViewById(R.id.wheel_picker_title);
        titleView.setText(dateMode ? R.string.offline_title_select_date : R.string.offline_title_select_time);
        previewView = content.findViewById(R.id.wheel_picker_preview);
        todayShortcut = content.findViewById(R.id.wheel_picker_today);
        haptics = new WheelHaptics(context);

        yearWheel = content.findViewById(R.id.wheel_year);
        monthWheel = content.findViewById(R.id.wheel_month);
        dayWheel = content.findViewById(R.id.wheel_day);
        hourWheel = content.findViewById(R.id.wheel_hour);
        minuteWheel = content.findViewById(R.id.wheel_minute);
        secondWheel = content.findViewById(R.id.wheel_second);

        boolean showDateColumns = dateMode;
        boolean showTimeColumns = !dateMode || includeTime;
        setColumnVisible(yearWheel, showDateColumns);
        setColumnVisible(monthWheel, showDateColumns);
        setColumnVisible(dayWheel, showDateColumns);
        setColumnVisible(hourWheel, showTimeColumns);
        setColumnVisible(minuteWheel, showTimeColumns);
        setColumnVisible(secondWheel, showTimeColumns && hasSecondColumn());

        if (showDateColumns) {
            prepareDateWheels();
        }
        if (showTimeColumns) {
            prepareTimeWheels();
        }
        attachHaptics();
        updatePreview();

        setColumnVisible(todayShortcut, dateMode);
        todayShortcut.setOnClickListener(v -> applyToday());

        content.findViewById(R.id.wheel_picker_cancel).setOnClickListener(v -> dialog.dismiss());
        content.findViewById(R.id.wheel_picker_confirm).setOnClickListener(v -> {
            String value = buildValue();
            dialog.dismiss();
            if (listener != null) {
                listener.onValueConfirmed(value);
            }
        });

        dialog.setContentView(content);
        dialog.show();

        // 滚轮自身需要消费纵向拖动，禁止抽屉跟手拖动，否则手势会被 BottomSheetBehavior 抢走。
        BottomSheetBehavior<FrameLayout> behavior = dialog.getBehavior();
        behavior.setDraggable(false);
        behavior.setFitToContents(true);
    }

    /**
     * 抽屉高度固定为视窗的 45%，滚轮行用 weight 吃掉剩余空间；视窗过矮时退回兜底高度，
     * 避免小屏（或横屏）下滚轮被压扁。
     */
    private void applySheetHeight(View content) {
        int targetHeight = Math.max(
                (int) (resolveViewportHeight() * SHEET_HEIGHT_RATIO),
                OfflineFormUiUnitHelper.dp(context, MIN_SHEET_HEIGHT_DP));
        content.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, targetHeight));
    }

    private int resolveViewportHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowManager windowManager = context.getSystemService(WindowManager.class);
            if (windowManager != null) {
                return windowManager.getCurrentWindowMetrics().getBounds().height();
            }
        }
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    private void setColumnVisible(View column, boolean visible) {
        column.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /** 时间列是否需要「秒」：日期 + 时间固定到秒，纯时间由配置决定。 */
    private boolean hasSecondColumn() {
        return includeTime || includeSeconds;
    }

    private void prepareDateWheels() {
        int year = clamp(initial.get(Calendar.YEAR), MIN_YEAR, MAX_YEAR);
        configureWheel(yearWheel);
        yearWheel.setData(buildYearLabels());
        yearWheel.setSelectedItemPosition(year - MIN_YEAR, false);

        configureWheel(monthWheel);
        monthWheel.setData(buildMonthLabels());
        monthWheel.setSelectedItemPosition(initial.get(Calendar.MONTH), false);

        configureWheel(dayWheel);
        dayWheel.setData(buildDayLabels(year, initial.get(Calendar.MONTH) + 1));
        dayWheel.setSelectedItemPosition(clamp(initial.get(Calendar.DAY_OF_MONTH), 1, dayWheel.getData().size()) - 1, false);

        // 年、月变化会影响当月天数，需要联动重建日列；三者都要刷新预览。
        WheelView.OnItemSelectedListener<String> yearMonthChanged = (wheelView, data, position) -> {
            rebuildDays();
            updatePreview();
        };
        yearWheel.setOnItemSelectedListener(yearMonthChanged);
        monthWheel.setOnItemSelectedListener(yearMonthChanged);
        dayWheel.setOnItemSelectedListener((wheelView, data, position) -> updatePreview());
    }

    private void prepareTimeWheels() {
        configureWheel(hourWheel);
        hourWheel.setCyclic(true);
        hourWheel.setData(buildTwoDigitLabels(0, 23));
        hourWheel.setSelectedItemPosition(initial.get(Calendar.HOUR_OF_DAY), false);

        configureWheel(minuteWheel);
        minuteWheel.setCyclic(true);
        minuteWheel.setData(buildTwoDigitLabels(0, 59));
        minuteWheel.setSelectedItemPosition(initial.get(Calendar.MINUTE), false);

        configureWheel(secondWheel);
        secondWheel.setCyclic(true);
        secondWheel.setData(buildTwoDigitLabels(0, 59));
        secondWheel.setSelectedItemPosition(initial.get(Calendar.SECOND), false);

        WheelView.OnItemSelectedListener<String> timeChanged = (wheelView, data, position) -> updatePreview();
        hourWheel.setOnItemSelectedListener(timeChanged);
        minuteWheel.setOnItemSelectedListener(timeChanged);
        secondWheel.setOnItemSelectedListener(timeChanged);
    }

    /**
     * 逐项滚动振动反馈。必须在初始选中位置设定之后再挂载，否则程序化定位会被当成用户滚动而误振。
     */
    private void attachHaptics() {
        WheelView.OnWheelChangedListener listener = new WheelView.OnWheelChangedListener() {
            @Override
            public void onWheelScroll(int scrollOffsetY) {
                // 逐像素滚动不需要反馈，只在跨项时振动。
            }

            @Override
            public void onWheelItemChanged(int oldPosition, int newPosition) {
                if (!rebuildingDays) {
                    haptics.tick();
                }
            }

            @Override
            public void onWheelSelected(int position) {
                // 停下后的选中事件不再重复振动。
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
                // 滚动状态变化无需反馈。
            }
        };

        yearWheel.setOnWheelChangedListener(listener);
        monthWheel.setOnWheelChangedListener(listener);
        dayWheel.setOnWheelChangedListener(listener);
        hourWheel.setOnWheelChangedListener(listener);
        minuteWheel.setOnWheelChangedListener(listener);
        secondWheel.setOnWheelChangedListener(listener);
    }

    /** 统一滚轮外观：选中行深色、其余浅色，细线分隔，柔和 3D 弧面。 */
    private void configureWheel(WheelView<String> wheel) {
        wheel.setVisibleItems(VISIBLE_ITEMS);
        wheel.setCurved(true);
        wheel.setCurvedArcDirection(WheelView.CURVED_ARC_DIRECTION_CENTER);
        wheel.setTextSize(17f, true);
        wheel.setLineSpacing(LINE_SPACING_DP, true);
        wheel.setTextAlign(WheelView.TEXT_ALIGN_CENTER);
        wheel.setNormalItemTextColor(ContextCompat.getColor(context, R.color.offline_form_muted));
        wheel.setSelectedItemTextColor(ContextCompat.getColor(context, R.color.offline_form_ink));
        wheel.setShowDivider(true);
        wheel.setDividerType(WheelView.DIVIDER_TYPE_WRAP);
        wheel.setDividerColor(ContextCompat.getColor(context, R.color.offline_form_line_strong));
        wheel.setDividerHeight(OfflineFormUiUnitHelper.dp(context, 1));
        wheel.setDividerPaddingForWrap(OfflineFormUiUnitHelper.dp(context, 6));
        wheel.setCyclic(false);
    }

    /** 年、月滚动后重算当月天数，并把越界的日（如 1/31 切到 2 月）收敛到当月最后一天。 */
    private void rebuildDays() {
        if (rebuildingDays || dayWheel == null || dayWheel.getData() == null || dayWheel.getData().isEmpty()) {
            return;
        }
        rebuildingDays = true;
        try {
            int year = selectedYear();
            int month = monthWheel.getSelectedItemPosition() + 1;
            int daysInMonth = daysInMonth(year, month);
            int day = clamp(dayWheel.getSelectedItemPosition() + 1, 1, daysInMonth);
            dayWheel.setData(buildDayLabels(year, month));
            dayWheel.setSelectedItemPosition(day - 1, false);
        } finally {
            rebuildingDays = false;
        }
    }

    /**
     * 快捷把日期滚轮跳到今天；时间滚轮保持用户当前选择（仅日期模式可用）。
     */
    private void applyToday() {
        Calendar today = Calendar.getInstance();
        int year = clamp(today.get(Calendar.YEAR), MIN_YEAR, MAX_YEAR);
        yearWheel.setSelectedItemPosition(year - MIN_YEAR, false);
        monthWheel.setSelectedItemPosition(today.get(Calendar.MONTH), false);
        // 年、月变化会通过联动回调重建日列，这里再定位到今天的日。
        dayWheel.setSelectedItemPosition(today.get(Calendar.DAY_OF_MONTH) - 1, false);
        updatePreview();
        haptics.tick();
    }

    /** 预览文案：带单位的完整结果，随滚轮滚动实时更新。 */
    private void updatePreview() {
        if (previewView == null) {
            return;
        }
        previewView.setText(buildPreview());
    }

    private CharSequence buildPreview() {
        if (!dateMode) {
            int hour = hourWheel.getSelectedItemPosition();
            int minute = minuteWheel.getSelectedItemPosition();
            return hasSecondColumn()
                    ? context.getString(R.string.offline_wheel_preview_time_seconds, hour, minute, secondWheel.getSelectedItemPosition())
                    : context.getString(R.string.offline_wheel_preview_time, hour, minute);
        }

        int year = selectedYear();
        int month = monthWheel.getSelectedItemPosition() + 1;
        int day = dayWheel.getSelectedItemPosition() + 1;
        if (!includeTime) {
            return context.getString(R.string.offline_wheel_preview_date, year, month, day);
        }
        return context.getString(
                R.string.offline_wheel_preview_date_time,
                year,
                month,
                day,
                hourWheel.getSelectedItemPosition(),
                minuteWheel.getSelectedItemPosition(),
                secondWheel.getSelectedItemPosition());
    }

    private String buildValue() {
        if (!dateMode) {
            int hour = hourWheel.getSelectedItemPosition();
            int minute = minuteWheel.getSelectedItemPosition();
            int second = secondWheel.getSelectedItemPosition();
            return hasSecondColumn()
                    ? String.format(Locale.CHINA, "%02d:%02d:%02d", hour, minute, second)
                    : String.format(Locale.CHINA, "%02d:%02d", hour, minute);
        }

        int year = selectedYear();
        int month = monthWheel.getSelectedItemPosition() + 1;
        int day = dayWheel.getSelectedItemPosition() + 1;
        if (!includeTime) {
            return String.format(Locale.CHINA, "%04d/%d/%d", year, month, day);
        }
        return String.format(
                Locale.CHINA,
                "%04d/%d/%d %02d:%02d:%02d",
                year,
                month,
                day,
                hourWheel.getSelectedItemPosition(),
                minuteWheel.getSelectedItemPosition(),
                secondWheel.getSelectedItemPosition());
    }

    private int selectedYear() {
        if (yearWheel == null || yearWheel.getData() == null || yearWheel.getData().isEmpty()) {
            return clamp(initial.get(Calendar.YEAR), MIN_YEAR, MAX_YEAR);
        }
        return MIN_YEAR + clamp(yearWheel.getSelectedItemPosition(), 0, MAX_YEAR - MIN_YEAR);
    }

    private List<String> buildYearLabels() {
        List<String> labels = new ArrayList<>(MAX_YEAR - MIN_YEAR + 1);
        for (int year = MIN_YEAR; year <= MAX_YEAR; year++) {
            labels.add(String.valueOf(year));
        }
        return labels;
    }

    private List<String> buildMonthLabels() {
        List<String> labels = new ArrayList<>(12);
        for (int month = 1; month <= 12; month++) {
            labels.add(String.valueOf(month));
        }
        return labels;
    }

    private List<String> buildDayLabels(int year, int month) {
        int daysInMonth = daysInMonth(year, month);
        List<String> labels = new ArrayList<>(daysInMonth);
        for (int day = 1; day <= daysInMonth; day++) {
            labels.add(String.valueOf(day));
        }
        return labels;
    }

    /** 时、分、秒统一补零，避免列宽随数值跳动。 */
    private List<String> buildTwoDigitLabels(int min, int max) {
        List<String> labels = new ArrayList<>(max - min + 1);
        for (int value = min; value <= max; value++) {
            labels.add(String.format(Locale.CHINA, "%02d", value));
        }
        return labels;
    }

    private static int daysInMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
