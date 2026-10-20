package com.huozige.lab.container.offlineform.formitem.picker;

import android.content.Context;
import android.os.Build;
import android.view.KeyEvent;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 从底部弹出的滚轮式日期/时间选择抽屉，用于替代系统自带的 DatePickerDialog、TimePickerDialog。
 *
 * <p>交互与主流移动端一致：滚轮在抽屉内滚动，点「确定」才提交；同一行滚轮按模式切换内容：
 * <ul>
 *     <li>仅日期：年 / 月 / 日，左下「取消」、右下「确定」</li>
 *     <li>仅时间：时 / 分（可选秒），左下「取消」、右下「确定」</li>
 *     <li>日期 + 时间：拆成两步——先选日期，右下「下一步」进入时间步骤；
 *     时间步骤左下变「上一步」，右下变「确定」</li>
 * </ul>
 *
 * <p>滚轮只显示数字，单位信息由标题下方的实时预览承担，滚动时同步刷新。
 *
 * <p>日期范围由插件配置决定：
 * <ul>
 *     <li>灵活选择（flexible）：按 minDate / maxDate 限定可选日期，留空表示不限制</li>
 *     <li>截止日期（deadline）：结束日期固定为今天（不能选未来），可用 minDate 指定开始日期；
 *     时间不受日期范围约束，始终是全天 24 小时</li>
 * </ul>
 *
 * <p>提交的值格式与改造前保持一致，避免影响表单 JSON 契约：
 * {@code yyyy/M/d}、{@code yyyy/M/d HH:mm:ss}、{@code HH:mm}、{@code HH:mm:ss}。
 */
public final class OfflineWheelPickerSheet {

    /** 值确认回调，仅在用户点「确定」时触发。 */
    public interface OnValueConfirmedListener {
        void onValueConfirmed(String value);
    }

    private static final int DEFAULT_MIN_YEAR = 1900;
    private static final int DEFAULT_MAX_YEAR = 2100;

    /** 滚轮可见条目数，取奇数以保证中间行严格居中；实际行数由固定的抽屉高度决定。 */
    private static final int VISIBLE_ITEMS = 13;

    /** 行间距（dp），让相邻选项之间留出呼吸感。 */
    private static final float LINE_SPACING_DP = 4f;

    /** 抽屉高度占视窗高度的比例。 */
    private static final float SHEET_HEIGHT_RATIO = 0.45f;

    /** 视窗过矮时的兜底高度（dp），保证滚轮至少留出几行可滚动区域。 */
    private static final int MIN_SHEET_HEIGHT_DP = 280;

    private static final String RANGE_MODE_DEADLINE = "deadline";

    private final Context context;
    private final Calendar initial;
    private final boolean dateMode;
    private final boolean includeTime;
    private final boolean includeSeconds;
    private final OnValueConfirmedListener listener;

    /** 可选日期下界，未配置时为默认年份范围的下限。 */
    private final Calendar minBound;
    /** 可选日期上界；截止日期模式下为「今天」。 */
    private final Calendar maxBound;

    private final List<Integer> yearValues = new ArrayList<>();
    private final List<Integer> monthValues = new ArrayList<>();
    private final List<Integer> dayValues = new ArrayList<>();
    private final List<Integer> hourValues = new ArrayList<>();
    private final List<Integer> minuteValues = new ArrayList<>();
    private final List<Integer> secondValues = new ArrayList<>();

    private WheelView<String> yearWheel;
    private WheelView<String> monthWheel;
    private WheelView<String> dayWheel;
    private WheelView<String> hourWheel;
    private WheelView<String> minuteWheel;
    private WheelView<String> secondWheel;
    private TextView titleView;
    private TextView previewView;
    private TextView cancelView;
    private TextView confirmView;
    private WheelHaptics haptics;
    private BottomSheetDialog dialog;

    /** 日期 + 时间模式下是否已进入「选时间」步骤。 */
    private boolean timeStep;

    /** 联动重建列表时置位，避免选中回调递归、也避免程序化改值误触振动。 */
    private boolean rebuilding;

    private OfflineWheelPickerSheet(
            Context context,
            Calendar initial,
            boolean dateMode,
            boolean includeTime,
            boolean includeSeconds,
            String dateRangeMode,
            String minDate,
            String maxDate,
            OnValueConfirmedListener listener) {
        this.context = context;
        this.initial = initial == null ? Calendar.getInstance() : (Calendar) initial.clone();
        this.dateMode = dateMode;
        this.includeTime = includeTime;
        this.includeSeconds = includeSeconds;
        this.listener = listener;

        boolean deadline = RANGE_MODE_DEADLINE.equalsIgnoreCase(dateRangeMode);
        Calendar parsedMin = parseDate(minDate);
        Calendar parsedMax = parseDate(maxDate);
        Calendar min = parsedMin == null ? defaultBound(DEFAULT_MIN_YEAR, 1, 1) : parsedMin;
        Calendar max;
        if (deadline) {
            // 截止日期：结束日期固定为今天，开始日期可配；时间不受日期范围约束。
            max = todayBound();
        } else {
            max = parsedMax == null ? defaultBound(DEFAULT_MAX_YEAR, 12, 31) : parsedMax;
        }
        if (min.after(max)) {
            // 配置颠倒时不阻断填报，退回默认的开始边界。
            min = defaultBound(DEFAULT_MIN_YEAR, 1, 1);
        }
        this.minBound = min;
        this.maxBound = max;
    }

    /** 打开日期选择抽屉：日期范围由插件下发的 dateRangeMode / minDate / maxDate 决定。 */
    public static void showDate(
            Context context,
            Calendar initial,
            boolean includeTime,
            String dateRangeMode,
            String minDate,
            String maxDate,
            OnValueConfirmedListener listener) {
        new OfflineWheelPickerSheet(context, initial, true, includeTime, false, dateRangeMode, minDate, maxDate, listener).show();
    }

    /** 打开时间选择抽屉。 */
    public static void showTime(
            Context context,
            Calendar initial,
            boolean includeSeconds,
            OnValueConfirmedListener listener) {
        new OfflineWheelPickerSheet(context, initial, false, false, includeSeconds, null, null, null, listener).show();
    }

    private void show() {
        dialog = new BottomSheetDialog(context);
        View content = LayoutInflater.from(context).inflate(R.layout.offline_form_wheel_picker_sheet, null);
        applySheetHeight(content);

        titleView = content.findViewById(R.id.wheel_picker_title);
        previewView = content.findViewById(R.id.wheel_picker_preview);
        cancelView = content.findViewById(R.id.wheel_picker_cancel);
        confirmView = content.findViewById(R.id.wheel_picker_confirm);
        haptics = new WheelHaptics(context);

        yearWheel = content.findViewById(R.id.wheel_year);
        monthWheel = content.findViewById(R.id.wheel_month);
        dayWheel = content.findViewById(R.id.wheel_day);
        hourWheel = content.findViewById(R.id.wheel_hour);
        minuteWheel = content.findViewById(R.id.wheel_minute);
        secondWheel = content.findViewById(R.id.wheel_second);

        // 两行滚轮都先备好数据，步骤切换只切显隐，避免切换时重建列表。
        if (dateMode) {
            prepareDateWheels();
        }
        if (!dateMode || includeTime) {
            prepareTimeWheels();
        }
        attachHaptics();
        updateStepUi();

        confirmView.setOnClickListener(v -> {
            if (dateMode && includeTime && !timeStep) {
                // 先选日期、再选时间：日期步骤点「下一步」进入时间步骤。
                timeStep = true;
                updateStepUi();
                haptics.tick();
                return;
            }
            commitAndDismiss(buildValue());
        });

        dialog.setContentView(content);
        dialog.setOnKeyListener((dialogInterface, keyCode, event) -> {
            // 时间步骤按返回键先退回日期步骤，再按一次才关闭抽屉。
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP && timeStep) {
                timeStep = false;
                updateStepUi();
                return true;
            }
            return false;
        });
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

    /** 按当前步骤刷新标题、左右按钮文案、滚轮显隐与预览。 */
    private void updateStepUi() {
        boolean showDateColumns = dateMode && !timeStep;
        boolean showTimeColumns = !dateMode || timeStep;

        setColumnVisible(yearWheel, showDateColumns);
        setColumnVisible(monthWheel, showDateColumns);
        setColumnVisible(dayWheel, showDateColumns);
        setColumnVisible(hourWheel, showTimeColumns);
        setColumnVisible(minuteWheel, showTimeColumns);
        setColumnVisible(secondWheel, showTimeColumns && hasSecondColumn());

        boolean hasNextStep = dateMode && includeTime;
        titleView.setText(showDateColumns ? R.string.offline_title_select_date : R.string.offline_title_select_time);
        confirmView.setText(hasNextStep && !timeStep ? R.string.offline_wheel_next : R.string.offline_button_confirm);
        cancelView.setText(hasNextStep && timeStep ? R.string.offline_wheel_previous : R.string.offline_button_cancel);
        cancelView.setOnClickListener(v -> {
            if (hasNextStep && timeStep) {
                timeStep = false;
                updateStepUi();
            } else {
                dialog.dismiss();
            }
        });
        updatePreview();
    }

    private void setColumnVisible(View column, boolean visible) {
        column.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /** 时间列是否需要「秒」：日期 + 时间固定到秒，纯时间由配置决定。 */
    private boolean hasSecondColumn() {
        return includeTime || includeSeconds;
    }

    // ---------------------------------------------------------------- 日期列

    private void prepareDateWheels() {
        configureWheel(yearWheel);
        configureWheel(monthWheel);
        configureWheel(dayWheel);

        rebuildYearValues();
        int year = clampInt(initial.get(Calendar.YEAR), minBound.get(Calendar.YEAR), maxBound.get(Calendar.YEAR));
        yearWheel.setData(toLabels(yearValues, false));
        yearWheel.setSelectedItemPosition(indexOf(yearValues, year), false);

        rebuildMonthValues(year);
        int month = clampInt(initial.get(Calendar.MONTH) + 1, firstMonth(year), lastMonth(year));
        monthWheel.setData(toLabels(monthValues, false));
        monthWheel.setSelectedItemPosition(indexOf(monthValues, month), false);

        rebuildDayValues(year, month);
        int day = clampInt(initial.get(Calendar.DAY_OF_MONTH), firstDay(year, month), lastDay(year, month));
        dayWheel.setData(toLabels(dayValues, false));
        dayWheel.setSelectedItemPosition(indexOf(dayValues, day), false);

        // 年、月变化会影响当月可选月份/天数，需要逐级重建；三者都要刷新预览。
        WheelView.OnItemSelectedListener<String> dateChanged = (wheelView, data, position) -> handleDateChanged();
        yearWheel.setOnItemSelectedListener(dateChanged);
        monthWheel.setOnItemSelectedListener(dateChanged);
        dayWheel.setOnItemSelectedListener(dateChanged);
    }

    /**
     * 年/月/日变化后：重建下级列表并把越界值收敛到区间内（如 1/31 切到 2 月变 28/29 日），
     * 截止日期模式下还要同步收敛时分秒上限。
     */
    private void handleDateChanged() {
        if (rebuilding) {
            return;
        }
        rebuilding = true;
        try {
            int year = selectedYear();
            int month = selectedMonth();
            int day = selectedDay();

            rebuildMonthValues(year);
            int newMonth = clampValue(month, monthValues);
            monthWheel.setData(toLabels(monthValues, false));
            monthWheel.setSelectedItemPosition(indexOf(monthValues, newMonth), false);

            rebuildDayValues(year, newMonth);
            int newDay = clampValue(day, dayValues);
            dayWheel.setData(toLabels(dayValues, false));
            dayWheel.setSelectedItemPosition(indexOf(dayValues, newDay), false);
        } finally {
            rebuilding = false;
        }
        updatePreview();
    }

    private void rebuildYearValues() {
        yearValues.clear();
        for (int year = minBound.get(Calendar.YEAR); year <= maxBound.get(Calendar.YEAR); year++) {
            yearValues.add(year);
        }
    }

    private int firstMonth(int year) {
        return year == minBound.get(Calendar.YEAR) ? minBound.get(Calendar.MONTH) + 1 : 1;
    }

    private int lastMonth(int year) {
        return year == maxBound.get(Calendar.YEAR) ? maxBound.get(Calendar.MONTH) + 1 : 12;
    }

    private void rebuildMonthValues(int year) {
        monthValues.clear();
        for (int month = firstMonth(year); month <= lastMonth(year); month++) {
            monthValues.add(month);
        }
    }

    private int firstDay(int year, int month) {
        boolean atMin = year == minBound.get(Calendar.YEAR) && month == minBound.get(Calendar.MONTH) + 1;
        return atMin ? minBound.get(Calendar.DAY_OF_MONTH) : 1;
    }

    private int lastDay(int year, int month) {
        int daysInMonth = daysInMonth(year, month);
        boolean atMax = year == maxBound.get(Calendar.YEAR) && month == maxBound.get(Calendar.MONTH) + 1;
        return atMax ? Math.min(maxBound.get(Calendar.DAY_OF_MONTH), daysInMonth) : daysInMonth;
    }

    private void rebuildDayValues(int year, int month) {
        dayValues.clear();
        for (int day = firstDay(year, month); day <= lastDay(year, month); day++) {
            dayValues.add(day);
        }
    }

    // ---------------------------------------------------------------- 时间列

    private void prepareTimeWheels() {
        configureWheel(hourWheel);
        configureWheel(minuteWheel);
        configureWheel(secondWheel);
        hourWheel.setCyclic(true);
        minuteWheel.setCyclic(true);
        secondWheel.setCyclic(true);
        buildFixedTimeValues();

        WheelView.OnItemSelectedListener<String> timeChanged = (wheelView, data, position) -> updatePreview();
        hourWheel.setOnItemSelectedListener(timeChanged);
        minuteWheel.setOnItemSelectedListener(timeChanged);
        secondWheel.setOnItemSelectedListener(timeChanged);
    }

    /** 时间列固定 0~23 / 0~59 / 0~59：日期范围只约束日期，时间始终是全天。 */
    private void buildFixedTimeValues() {
        fillValues(hourValues, 0, 23);
        hourWheel.setData(toLabels(hourValues, true));
        hourWheel.setSelectedItemPosition(indexOf(hourValues, clampInt(initial.get(Calendar.HOUR_OF_DAY), 0, 23)), false);

        fillValues(minuteValues, 0, 59);
        minuteWheel.setData(toLabels(minuteValues, true));
        minuteWheel.setSelectedItemPosition(indexOf(minuteValues, clampInt(initial.get(Calendar.MINUTE), 0, 59)), false);

        fillValues(secondValues, 0, 59);
        secondWheel.setData(toLabels(secondValues, true));
        secondWheel.setSelectedItemPosition(indexOf(secondValues, clampInt(initial.get(Calendar.SECOND), 0, 59)), false);
    }

    private static void fillValues(List<Integer> values, int min, int max) {
        values.clear();
        for (int value = min; value <= max; value++) {
            values.add(value);
        }
    }

    // ---------------------------------------------------------------- 外观与反馈

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
                if (!rebuilding) {
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
        wheel.setTextSize(20f, true);
        wheel.setLineSpacing(LINE_SPACING_DP, true);
        wheel.setTextAlign(WheelView.TEXT_ALIGN_CENTER);
        wheel.setNormalItemTextColor(ContextCompat.getColor(context, R.color.offline_form_muted));
        wheel.setSelectedItemTextColor(ContextCompat.getColor(context, R.color.offline_form_ink));
        wheel.setShowDivider(true);
        wheel.setDividerType(WheelView.DIVIDER_TYPE_WRAP);
        wheel.setDividerColor(ContextCompat.getColor(context, R.color.offline_form_line_strong));
        wheel.setDividerHeight(OfflineFormUiUnitHelper.dp(context, 1));
        wheel.setDividerPaddingForWrap(OfflineFormUiUnitHelper.dp(context, 8));
        wheel.setCyclic(false);
    }

    // ---------------------------------------------------------------- 取值与预览

    /** 提交结果：先关闭抽屉，再把值回填给调用方。 */
    private void commitAndDismiss(String value) {
        dialog.dismiss();
        if (listener != null) {
            listener.onValueConfirmed(value);
        }
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
            int hour = selectedHour();
            int minute = selectedMinute();
            return hasSecondColumn()
                    ? context.getString(R.string.offline_wheel_preview_time_seconds, hour, minute, selectedSecond())
                    : context.getString(R.string.offline_wheel_preview_time, hour, minute);
        }

        int year = selectedYear();
        int month = selectedMonth();
        int day = selectedDay();
        if (!includeTime || !timeStep) {
            return context.getString(R.string.offline_wheel_preview_date, year, month, day);
        }
        return context.getString(
                R.string.offline_wheel_preview_date_time,
                year,
                month,
                day,
                selectedHour(),
                selectedMinute(),
                selectedSecond());
    }

    private String buildValue() {
        if (!dateMode) {
            int hour = selectedHour();
            int minute = selectedMinute();
            int second = selectedSecond();
            return hasSecondColumn()
                    ? String.format(Locale.CHINA, "%02d:%02d:%02d", hour, minute, second)
                    : String.format(Locale.CHINA, "%02d:%02d", hour, minute);
        }

        int year = selectedYear();
        int month = selectedMonth();
        int day = selectedDay();
        if (!includeTime) {
            return String.format(Locale.CHINA, "%04d/%d/%d", year, month, day);
        }
        return String.format(
                Locale.CHINA,
                "%04d/%d/%d %02d:%02d:%02d",
                year,
                month,
                day,
                selectedHour(),
                selectedMinute(),
                selectedSecond());
    }

    private int selectedYear() {
        return valueAt(yearValues, yearWheel, initial.get(Calendar.YEAR));
    }

    private int selectedMonth() {
        return valueAt(monthValues, monthWheel, initial.get(Calendar.MONTH) + 1);
    }

    private int selectedDay() {
        return valueAt(dayValues, dayWheel, initial.get(Calendar.DAY_OF_MONTH));
    }

    private int selectedHour() {
        return valueAt(hourValues, hourWheel, initial.get(Calendar.HOUR_OF_DAY));
    }

    private int selectedMinute() {
        return valueAt(minuteValues, minuteWheel, initial.get(Calendar.MINUTE));
    }

    private int selectedSecond() {
        return valueAt(secondValues, secondWheel, initial.get(Calendar.SECOND));
    }

    private static int valueAt(List<Integer> values, WheelView<String> wheel, int fallback) {
        if (values.isEmpty() || wheel == null) {
            return fallback;
        }
        int position = clampInt(wheel.getSelectedItemPosition(), 0, values.size() - 1);
        return values.get(position);
    }

    private static List<String> toLabels(List<Integer> values, boolean twoDigits) {
        List<String> labels = new ArrayList<>(values.size());
        for (Integer value : values) {
            labels.add(twoDigits ? String.format(Locale.CHINA, "%02d", value) : String.valueOf(value));
        }
        return labels;
    }

    private static int indexOf(List<Integer> values, int value) {
        int index = values.indexOf(value);
        if (index >= 0) {
            return index;
        }
        return value < values.get(0) ? 0 : values.size() - 1;
    }

    private static int clampValue(int value, List<Integer> values) {
        return clampInt(value, values.get(0), values.get(values.size() - 1));
    }

    // ---------------------------------------------------------------- 工具

    private static int clampInt(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private static int daysInMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private static Calendar defaultBound(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month - 1, day);
        return calendar;
    }

    private static Calendar todayBound() {
        Calendar today = Calendar.getInstance();
        return defaultBound(
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH) + 1,
                today.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 解析插件下发的日期配置，兼容常见写法；解析失败返回 null，由调用方退回默认范围。
     * 日期范围只关心日期，解析后会把时分秒清零。
     */
    private static Calendar parseDate(String text) {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        String[] patterns = {
                "yyyy/M/d HH:mm:ss",
                "yyyy/M/d HH:mm",
                "yyyy/M/d",
                "yyyy-M-d HH:mm:ss",
                "yyyy-M-d HH:mm",
                "yyyy-M-d",
                "yyyy.M.d",
                "yyyyMMdd"
        };
        for (String pattern : patterns) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
                format.setLenient(false);
                Date parsed = format.parse(text.trim());
                if (parsed != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(parsed);
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    return calendar;
                }
            } catch (ParseException ignored) {
                // 尝试下一个兼容格式。
            }
        }
        return null;
    }
}
