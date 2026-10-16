package com.huozige.lab.container.preferences;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceViewHolder;

import com.huozige.lab.container.R;

public class ScanEditTextPreference extends EditTextPreference {

    private View.OnClickListener _scanClickListener;

    public ScanEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScanClickListener(View.OnClickListener scanClickListener) {
        _scanClickListener = scanClickListener;
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        // 这个按钮来自 preference_widget_scan.xml，相当于给输入项增加一个右侧扫码后缀按钮。
        View scanButton = holder.findViewById(R.id.cmdPreferenceScan);
        if (scanButton != null) {
            scanButton.setOnClickListener(_scanClickListener);
        }
    }
}
