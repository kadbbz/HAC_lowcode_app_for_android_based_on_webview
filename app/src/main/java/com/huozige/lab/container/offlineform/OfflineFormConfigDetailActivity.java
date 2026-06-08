package com.huozige.lab.container.offlineform;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.huozige.lab.container.BaseActivity;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.OfflineComputedInfo;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFlattener;
import com.huozige.lab.container.offlineform.model.OfflineFormRecordStatus;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinition;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OfflineFormConfigDetailActivity extends BaseActivity {
    public static final String EXTRA_PATTERN_ID = "patternId";
    private static final Integer[] RECORD_PAGE_SIZE_OPTIONS = new Integer[]{1, 10, 20, 50, 100};

    private String _patternId;
    private OfflineFormDefinitionFile _definitionFile;
    private boolean _recordPageSizeRendering;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.offline_menu_config_detail);
        setContentView(R.layout.offline_form_config_detail_activity);

        _patternId = getIntent().getStringExtra(EXTRA_PATTERN_ID);
        findViewById(R.id.cmdDeleteDraftRecords).setOnClickListener(v -> confirmDeleteRecordsByStatus(
                OfflineFormRecordStatus.DRAFT,
                R.string.offline_dialog_delete_draft_records_message));
        findViewById(R.id.cmdDeleteExportedRecords).setOnClickListener(v -> confirmDeleteRecordsByStatus(
                OfflineFormRecordStatus.EXPORTED,
                R.string.offline_dialog_delete_exported_records_message));
        findViewById(R.id.cmdDeleteConfig).setOnClickListener(v -> confirmDeleteConfig());
        renderConfig();
    }

    private void renderConfig() {
        if (_patternId == null || _patternId.isEmpty()) {
            Toast.makeText(this, R.string.offline_toast_config_missing, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        _definitionFile = OfflineFormFileHelper.readDefinition(this, _patternId);
        if (_definitionFile == null) {
            Toast.makeText(this, R.string.offline_toast_config_missing, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        OfflineFormDefinition definition = _definitionFile.getJsonSchema();
        String definitionFilePath = OfflineFormFileHelper.getDefinitionFilePath(this, _patternId);

        TextView basicInfoTextView = findViewById(R.id.basicInfoTextView);
        basicInfoTextView.setText(getString(
                R.string.offline_text_config_basic_info,
                definition.getPatternId(),
                definition.getSchemaVersion(),
                definition.getTitle(),
                definition.getDescription(),
                OfflineFormDefinitionFlattener.flattenFields(definition).size(),
                definitionFilePath));

        renderDisplayColumns(definition);
        renderRecordPageSize();
        refreshRawConfig();
    }

    private void renderRecordPageSize() {
        Spinner recordPageSizeSpinner = findViewById(R.id.recordPageSizeSpinner);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, RECORD_PAGE_SIZE_OPTIONS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recordPageSizeSpinner.setAdapter(adapter);

        _recordPageSizeRendering = true;
        int recordPageSize = getRecordPageSize();
        int selectedIndex = 0;
        for (int i = 0; i < RECORD_PAGE_SIZE_OPTIONS.length; i++) {
            if (RECORD_PAGE_SIZE_OPTIONS[i] == recordPageSize) {
                selectedIndex = i;
                break;
            }
        }
        recordPageSizeSpinner.setSelection(selectedIndex);
        _recordPageSizeRendering = false;

        recordPageSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (_recordPageSizeRendering) {
                    return;
                }
                saveRecordPageSize(RECORD_PAGE_SIZE_OPTIONS[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void renderDisplayColumns(OfflineFormDefinition definition) {
        LinearLayout displayColumnListLayout = findViewById(R.id.displayColumnListLayout);
        displayColumnListLayout.removeAllViews();

        List<String> displayColumns = getDisplayColumns();
        for (BaseFormItem formItem : OfflineFormDefinitionFlattener.flattenFields(definition)) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(formItem.getTitle() == null || formItem.getTitle().isEmpty() ? formItem.getId() : formItem.getTitle());
            checkBox.setTextSize(14);
            checkBox.setTag(formItem.getId());
            checkBox.setChecked(displayColumns.contains(formItem.getId()));
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> saveDisplayColumns());
            displayColumnListLayout.addView(checkBox);
        }
    }

    private void saveDisplayColumns() {
        if (_definitionFile == null || _definitionFile.getComputed() == null) {
            return;
        }

        LinearLayout displayColumnListLayout = findViewById(R.id.displayColumnListLayout);
        List<String> selectedColumns = new ArrayList<>();
        int checkBoxCount = displayColumnListLayout.getChildCount();
        for (int i = 0; i < checkBoxCount; i++) {
            CheckBox checkBox = (CheckBox) displayColumnListLayout.getChildAt(i);
            if (checkBox.isChecked()) {
                selectedColumns.add((String) checkBox.getTag());
            }
        }

        _definitionFile.getComputed().setDisplayColumns(selectedColumns);
        OfflineFormFileHelper.writeDefinition(this, _patternId, _definitionFile);
        refreshRawConfig();
    }

    private List<String> getDisplayColumns() {
        if (_definitionFile == null
                || _definitionFile.getComputed() == null
                || _definitionFile.getComputed().getDisplayColumns() == null) {
            return new ArrayList<>();
        }
        return _definitionFile.getComputed().getDisplayColumns();
    }

    private int getRecordPageSize() {
        if (_definitionFile == null || _definitionFile.getComputed() == null) {
            return OfflineComputedInfo.DEFAULT_RECORD_PAGE_SIZE;
        }
        return normalizeRecordPageSize(_definitionFile.getComputed().getRecordPageSize());
    }

    private int normalizeRecordPageSize(int pageSize) {
        for (int option : RECORD_PAGE_SIZE_OPTIONS) {
            if (option == pageSize) {
                return pageSize;
            }
        }
        return OfflineComputedInfo.DEFAULT_RECORD_PAGE_SIZE;
    }

    private void saveRecordPageSize(int recordPageSize) {
        if (_definitionFile == null || _definitionFile.getComputed() == null) {
            return;
        }

        _definitionFile.getComputed().setRecordPageSize(recordPageSize);
        OfflineFormFileHelper.writeDefinition(this, _patternId, _definitionFile);
        refreshRawConfig();
    }

    private void refreshRawConfig() {
        JSONObject rawDefinitionJson = OfflineFormFileHelper.readRawDefinitionJson(this, _patternId);
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

    private void confirmDeleteRecordsByStatus(OfflineFormRecordStatus status, int messageResId) {
        if (_patternId == null || _patternId.isEmpty()) {
            Toast.makeText(this, R.string.offline_toast_config_missing, Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.offline_dialog_delete_title)
                .setMessage(messageResId)
                .setPositiveButton(R.string.offline_button_delete, (dialog, which) -> deleteRecordsByStatus(status))
                .setNegativeButton(R.string.ui_button_cancel, null)
                .show();
    }

    private void deleteRecordsByStatus(OfflineFormRecordStatus status) {
        int deletedCount = OfflineFormFileHelper.deleteRecordsByStatus(this, _patternId, status);
        Toast.makeText(this, getString(R.string.offline_toast_delete_records_success, deletedCount), Toast.LENGTH_SHORT).show();
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
