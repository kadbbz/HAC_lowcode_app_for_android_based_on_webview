package com.huozige.lab.container;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineFormDefinition;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineFormDefinitionFile;

import org.json.JSONException;
import org.json.JSONObject;

public class OfflineFormConfigDetailActivity extends BaseActivity {
    public static final String EXTRA_PATTERN_ID = "patternId";

    private String _patternId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.offline_menu_config_detail);
        setContentView(R.layout.offline_form_config_detail_activity);

        _patternId = getIntent().getStringExtra(EXTRA_PATTERN_ID);
        findViewById(R.id.cmdDeleteConfig).setOnClickListener(v -> confirmDeleteConfig());
        renderConfig();
    }

    private void renderConfig() {
        if (_patternId == null || _patternId.isEmpty()) {
            Toast.makeText(this, R.string.offline_toast_config_missing, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        OfflineFormDefinitionFile definitionFile = OfflineFormFileHelper.readDefinition(this, _patternId);
        if (definitionFile == null) {
            Toast.makeText(this, R.string.offline_toast_config_missing, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        OfflineFormDefinition definition = definitionFile.getJsonSchema();
        String definitionFilePath = OfflineFormFileHelper.getDefinitionFilePath(this, _patternId);
        JSONObject rawDefinitionJson = OfflineFormFileHelper.readRawDefinitionJson(this, _patternId);

        TextView basicInfoTextView = findViewById(R.id.basicInfoTextView);
        basicInfoTextView.setText(getString(
                R.string.offline_text_config_basic_info,
                definition.getPatternId(),
                definition.getSchemaVersion(),
                definition.getTitle(),
                definition.getDescription(),
                definition.getFormItems().size(),
                definitionFilePath));

        TextView rawConfigTextView = findViewById(R.id.rawConfigTextView);
        rawConfigTextView.setText(formatJson(rawDefinitionJson));
    }

    private String formatJson(JSONObject jsonObject) {
        if (jsonObject == null) {
            return "";
        }
        try {
            return jsonObject.toString(2);
        } catch (JSONException e) {
            return jsonObject.toString();
        }
    }

    private void confirmDeleteConfig() {
        if (_patternId == null || _patternId.isEmpty()) {
            Toast.makeText(this, R.string.offline_toast_config_missing, Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.offline_dialog_delete_title)
                .setMessage(R.string.offline_dialog_delete_config_message)
                .setPositiveButton(R.string.offline_button_delete, (dialog, which) -> deleteConfig())
                .setNegativeButton(R.string.ui_button_cancel, null)
                .show();
    }

    private void deleteConfig() {
        if (!OfflineFormFileHelper.deletePatternDirectory(this, _patternId)) {
            Toast.makeText(this, R.string.offline_toast_delete_failed, Toast.LENGTH_SHORT).show();
            return;
        }

        OfflineFormFileHelper.removeDefinitionOrder(this, _patternId);
        Toast.makeText(this, R.string.offline_toast_delete_success, Toast.LENGTH_SHORT).show();
        finish();
    }
}
