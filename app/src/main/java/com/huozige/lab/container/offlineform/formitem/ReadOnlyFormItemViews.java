package com.huozige.lab.container.offlineform.formitem;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public final class ReadOnlyFormItemViews {
    private ReadOnlyFormItemViews() {
    }

    public static TextView createCompactValueView(Context context, String text) {
        TextView textView = new TextView(context);
        int densityPaddingHorizontal = dp(context, 12);
        int densityPaddingVertical = dp(context, 10);
        textView.setMinWidth(dp(context, 120));
        textView.setMaxWidth(dp(context, 220));
        textView.setPadding(densityPaddingHorizontal, densityPaddingVertical, densityPaddingHorizontal, densityPaddingVertical);
        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setText(text == null ? "" : text);
        textView.setTextSize(13);
        textView.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray));
        textView.setEnabled(false);
        return textView;
    }

    private static int dp(Context context, int value) {
        return (int) (value * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
