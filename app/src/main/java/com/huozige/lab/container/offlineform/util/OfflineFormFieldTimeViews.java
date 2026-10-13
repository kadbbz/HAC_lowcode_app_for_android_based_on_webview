package com.huozige.lab.container.offlineform.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huozige.lab.container.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 在填报页字段控件底部展示"字段更新时间"。
 *
 * <p>时间来源为 {@code OfflineFormRecord#getFieldUpdateTimes()}，仅在字段发生过变更
 * （有打点）时展示；列表子字段的时间按所在行取值。</p>
 */
public final class OfflineFormFieldTimeViews {

    private OfflineFormFieldTimeViews() {
    }

    /**
     * 在字段控件底部显示/隐藏更新时间行。
     *
     * @param itemView 字段控件的根布局
     * @param rawTime  字段更新时间（ODate 数值）；为 null 或不合法时移除时间行
     */
    public static void apply(View itemView, Object rawTime) {
        if (!(itemView instanceof ViewGroup) || !(itemView instanceof LinearLayout)) {
            return;
        }
        LinearLayout container = (LinearLayout) itemView;
        TextView timeView = container.findViewById(R.id.offline_field_update_time);
        Double time = asTime(rawTime);

        if (time == null) {
            if (timeView != null) {
                container.removeView(timeView);
            }
            return;
        }

        if (timeView == null) {
            timeView = createTimeView(itemView.getContext());
            container.addView(timeView, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        timeView.setText(itemView.getContext().getString(
                R.string.offline_text_field_update_time, format(time)));
    }

    private static TextView createTimeView(Context context) {
        TextView timeView = new TextView(context);
        timeView.setId(R.id.offline_field_update_time);
        timeView.setTextSize(12);
        timeView.setTextColor(context.getColor(R.color.offline_form_document_meta));
        timeView.setPadding(0, OfflineFormUiUnitHelper.dp(context, 4), 0, 0);
        return timeView;
    }

    /** 将 ODate 数值格式化为 "yyyy-MM-dd HH:mm:ss"。 */
    public static String format(double oDate) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
                .format(new Date(ODateUtils.toUnixMillis(oDate)));
    }

    private static Double asTime(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (NumberFormatException ignored) {
                return null;
            }
        }
        return null;
    }
}
