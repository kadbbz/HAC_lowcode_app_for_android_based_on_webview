package com.huozige.lab.container.offlineform;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.huozige.lab.container.BaseActivity;
import com.huozige.lab.container.R;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class OfflineFormRawConfigActivity extends BaseActivity {
    public static final String EXTRA_PATTERN_ID = "patternId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.offline_title_raw_config);
        setContentView(R.layout.offline_form_raw_config_activity);

        String patternId = getIntent().getStringExtra(EXTRA_PATTERN_ID);
        if (patternId == null || patternId.isEmpty()) {
            Toast.makeText(this, R.string.offline_toast_config_missing, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        JSONObject rawDefinitionJson = OfflineFormFileHelper.readRawDefinitionJson(this, patternId);
        if (rawDefinitionJson == null) {
            Toast.makeText(this, R.string.offline_toast_config_missing, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        TextView rawConfigTextView = findViewById(R.id.rawConfigTextView);
        rawConfigTextView.setText(formatJson(rawDefinitionJson));
    }

    private String formatJson(JSONObject jsonObject) {
        try {
            return jsonObject.toString(2);
        } catch (JSONException e) {
            return jsonObject.toString();
        }
    }
}
