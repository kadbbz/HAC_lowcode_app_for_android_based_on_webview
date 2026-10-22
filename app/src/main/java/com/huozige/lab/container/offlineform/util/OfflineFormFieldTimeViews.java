package com.huozige.lab.container.offlineform.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.formitem.ResponsiveFieldRow;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 在填报页字段的右上角展示"字段更新时间"。
 *
 * <p>位置：与字段名同一行、靠右、垂直居中；开关这类「标签 + 控件」左右排列的组件，
 * 由 {@link ResponsiveFieldRow} 把更新时间放在标题行右侧，控件顺延到下一行，
 * 避免时间与开关抢同一行空间。</p>
 *
 * <p>文案：{@code 更新于：<智能时间>}；智能时间按相对时间展示——刚刚 / N分钟前 / N小时前 /
 * 昨天 HH:mm / MM-dd HH:mm / yyyy-MM-dd HH:mm。相对时间会随当前时间变化，
 * 页面在前台时由定时器每 30 秒刷新一次。</p>
 *
 * <p>时间来源为 {@code OfflineFormRecord#getFieldUpdateTimes()}，仅在字段发生过变更
 * （有打点）时展示；列表子字段的时间按所在行取值。</p>
 */
public final class OfflineFormFieldTimeViews {

    // 相对时间文案会随分钟变化，前台页面定时刷新即可；间隔取 30 秒，跨分钟时最多滞后半分钟。
    private static final long REFRESH_INTERVAL_MS = 30_000L;
    // 更新时间与字段名之间的最小间距。
    private static final int TIME_MARGIN_START_DP = 8;
    private static final int TIME_TEXT_SIZE_SP = 12;

    // 当前挂在界面上的更新时间视图 → 该字段更新时间的 Unix 毫秒值，用于定时刷新相对时间。
    private static final Map<TextView, Long> ACTIVE_TIME_VIEWS = new WeakHashMap<>();
    private static final Handler REFRESH_HANDLER = new Handler(Looper.getMainLooper());
    private static final Runnable REFRESH_RUNNABLE = new Runnable() {
        @Override
        public void run() {
            long now = System.currentTimeMillis();
            boolean hasActiveView = false;
            Iterator<Map.Entry<TextView, Long>> iterator = ACTIVE_TIME_VIEWS.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<TextView, Long> entry = iterator.next();
                TextView timeView = entry.getKey();
                if (timeView == null || timeView.getParent() == null || !timeView.isAttachedToWindow()) {
                    iterator.remove();
                    continue;
                }
                timeView.setText(buildText(timeView.getContext(), entry.getValue(), now));
                hasActiveView = true;
            }
            if (hasActiveView) {
                REFRESH_HANDLER.postDelayed(this, REFRESH_INTERVAL_MS);
            }
        }
    };

    private OfflineFormFieldTimeViews() {
    }

    /**
     * 在字段组件右上角显示/隐藏更新时间。
     *
     * @param itemView 字段组件的根布局
     * @param rawTime  字段更新时间（ODate 数值或等价的字符串）；为 null 或不合法时移除时间文本
     */
    public static void apply(View itemView, Object rawTime) {
        if (!(itemView instanceof LinearLayout)) {
            return;
        }
        LinearLayout root = (LinearLayout) itemView;
        Long updateTime = asUnixMillis(rawTime);
        TextView timeView = root.findViewById(R.id.offline_field_update_time);

        if (updateTime == null) {
            if (timeView != null) {
                removeFromParent(timeView);
                ACTIVE_TIME_VIEWS.remove(timeView);
            }
            return;
        }

        if (timeView == null) {
            timeView = createTimeView(root.getContext());
        }
        placeTimeView(root, timeView);
        timeView.setText(buildText(root.getContext(), updateTime, System.currentTimeMillis()));
        ACTIVE_TIME_VIEWS.put(timeView, updateTime);
        scheduleRefresh();
    }

    /**
     * 把时间文本放到字段名所在行的右侧（右上角）；没有标准标签行时退化为组件顶部一行右对齐。
     */
    private static void placeTimeView(LinearLayout root, TextView timeView) {
        LinearLayout labelRow = findLabelRow(root);
        if (labelRow != null && placeInLabelRow(labelRow, root, timeView)) {
            return;
        }
        placeAtComponentTop(root, timeView);
    }

    /**
     * 放到字段名同一行右侧。
     *
     * @return 是否已完成放置；开关组件的标签行由 {@link ResponsiveFieldRow} 承载时复用其第三子 View 槽位
     */
    private static boolean placeInLabelRow(LinearLayout labelRow, LinearLayout root, TextView timeView) {
        ViewParent labelRowParent = labelRow.getParent();
        if (labelRowParent instanceof ResponsiveFieldRow) {
            // 开关组件：ResponsiveFieldRow 的第 3 个子 View 是标题行右侧的元信息；
            // 它会把控件顺延到下一行，避免更新时间与开关争抢同一行的右侧空间。
            ResponsiveFieldRow responsiveRow = (ResponsiveFieldRow) labelRowParent;
            if (timeView.getParent() != responsiveRow) {
                removeFromParent(timeView);
                responsiveRow.addView(timeView, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            timeView.setGravity(Gravity.END);
            return true;
        }

        // 普通组件：标签行右侧默认是空位。若行内已有会吃掉剩余宽度的计数文本
        // （图片张数 / 签名人数等），时间改用自适应宽度追加在其后；否则时间占满剩余宽度、文字靠右。
        boolean flexible = !hasVisibleWeightedChild(labelRow, timeView);
        LinearLayout.LayoutParams desired = new LinearLayout.LayoutParams(
                flexible ? 0 : ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                flexible ? 1f : 0f);
        desired.gravity = Gravity.CENTER_VERTICAL;
        desired.setMarginStart(OfflineFormUiUnitHelper.dp(root.getContext(), TIME_MARGIN_START_DP));
        if (timeView.getParent() != labelRow || !fitsLabelRow(timeView, labelRow, flexible)) {
            removeFromParent(timeView);
            labelRow.addView(timeView, desired);
        }
        timeView.setGravity(Gravity.END);
        return true;
    }

    /** 兜底：找不到标准标签行时，在组件顶部单独占一行并靠右。 */
    private static void placeAtComponentTop(LinearLayout root, TextView timeView) {
        boolean needsAdd = timeView.getParent() != root || root.indexOfChild(timeView) != 0;
        if (needsAdd) {
            removeFromParent(timeView);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            root.addView(timeView, 0, params);
        }
        timeView.setGravity(Gravity.END);
    }

    /** 字段名所在的横向标签行；找不到时返回 null。 */
    private static LinearLayout findLabelRow(LinearLayout root) {
        View titleView = root.findViewById(R.id.tv_title);
        if (titleView == null) {
            return null;
        }
        ViewParent parent = titleView.getParent();
        if (parent instanceof LinearLayout && ((LinearLayout) parent).getOrientation() == LinearLayout.HORIZONTAL) {
            return (LinearLayout) parent;
        }
        return null;
    }

    /** 标签行里是否已有可见的、带 weight 的子 View（例如图片张数、签名人数）。 */
    private static boolean hasVisibleWeightedChild(LinearLayout labelRow, View excluded) {
        for (int i = 0; i < labelRow.getChildCount(); i++) {
            View child = labelRow.getChildAt(i);
            if (child == excluded || child.getVisibility() != View.VISIBLE) {
                continue;
            }
            ViewGroup.LayoutParams params = child.getLayoutParams();
            if (params instanceof LinearLayout.LayoutParams
                    && ((LinearLayout.LayoutParams) params).weight > 0) {
                return true;
            }
        }
        return false;
    }

    /** 判断时间文本当前的布局参数是否仍符合「自适应宽度 / 固定宽度」的预期，避免重复 addView 造成闪烁。 */
    private static boolean fitsLabelRow(TextView timeView, LinearLayout labelRow, boolean flexible) {
        if (timeView.getParent() != labelRow) {
            return false;
        }
        ViewGroup.LayoutParams params = timeView.getLayoutParams();
        if (!(params instanceof LinearLayout.LayoutParams)) {
            return false;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) params;
        int expectedWidth = flexible ? 0 : LinearLayout.LayoutParams.WRAP_CONTENT;
        float expectedWeight = flexible ? 1f : 0f;
        return layoutParams.width == expectedWidth && layoutParams.weight == expectedWeight;
    }

    private static TextView createTimeView(Context context) {
        TextView timeView = new TextView(context);
        timeView.setId(R.id.offline_field_update_time);
        timeView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TIME_TEXT_SIZE_SP);
        timeView.setTextColor(context.getColor(R.color.offline_form_document_meta));
        timeView.setSingleLine(true);
        timeView.setGravity(Gravity.END);
        return timeView;
    }

    private static String buildText(Context context, long unixMillis, long nowMillis) {
        return context.getString(
                R.string.offline_text_field_update_time,
                OfflineSmartTime.format(context, unixMillis, nowMillis));
    }

    private static void scheduleRefresh() {
        REFRESH_HANDLER.removeCallbacks(REFRESH_RUNNABLE);
        REFRESH_HANDLER.postDelayed(REFRESH_RUNNABLE, REFRESH_INTERVAL_MS);
    }

    private static void removeFromParent(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
    }

    /** 把字段更新时间（ODate 数值或等价的字符串）转换为 Unix 毫秒；无法识别时返回 null。 */
    private static Long asUnixMillis(Object value) {
        Double oDate = null;
        if (value instanceof Number) {
            oDate = ((Number) value).doubleValue();
        } else if (value instanceof String) {
            try {
                oDate = Double.parseDouble((String) value);
            } catch (NumberFormatException ignored) {
                return null;
            }
        }
        if (oDate == null || oDate.isNaN() || oDate.isInfinite()) {
            return null;
        }
        return ODateUtils.toUnixMillis(oDate);
    }
}
