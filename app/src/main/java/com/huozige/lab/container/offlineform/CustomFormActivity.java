package com.huozige.lab.container.offlineform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinition;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFlattener;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;
import com.huozige.lab.container.offlineform.model.OfflineFormStep;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.SelectFormItem;
import com.huozige.lab.container.offlineform.model.formitem.TextFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.FormAdapter;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomFormActivity extends AppCompatActivity {
    public static final String EXTRA_RECORD_ID = "recordId";

    private RecyclerView _recyclerView;
    private FormAdapter _adapter;
    private Button _btnPreviousStep;
    private Button _btnSubmit;
    private Intent _intent;
    private boolean _formLoaded;
    private boolean _editRecordMissingOrOutdated;
    private OfflineFormRecord _editingRecord;
    private OfflineFormDefinition _definition;
    private int _currentStepIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_form_custom_form_activity);

        _intent = getIntent();

        initViews();
        setupRecyclerView();
        loadFormDataFromJson();
        setupListeners();
    }

    private void initViews() {
        _recyclerView = findViewById(R.id.recycler_view);
        _btnPreviousStep = findViewById(R.id.btn_previous_step);
        _btnSubmit = findViewById(R.id.btn_submit);
    }

    private void setupRecyclerView() {
        _adapter = new FormAdapter();
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
        _recyclerView.setAdapter(_adapter);
    }

    private void loadFormDataFromJson() {

        String patternId = _intent.getStringExtra("patternId");
        if (patternId == null || patternId.isEmpty()) {
            Toast.makeText(this, R.string.offline_toast_config_missing, Toast.LENGTH_SHORT).show();
            _adapter.setDisplayItems(new ArrayList<>());
            return;
        }

        try {
            OfflineFormDefinitionFile definitionFile = OfflineFormFileHelper.readDefinition(this, patternId);
            if (definitionFile != null && patternId.equals(definitionFile.getJsonSchema().getPatternId())) {
                _definition = definitionFile.getJsonSchema();
                loadEditingRecord(patternId, _definition.getSchemaVersion(), OfflineFormDefinitionFlattener.flattenFields(_definition));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.offline_toast_parse_form_failed, e.getMessage()), Toast.LENGTH_LONG).show();
        }
        _formLoaded = _definition != null && _definition.getSteps() != null && !_definition.getSteps().isEmpty();
        renderCurrentStep();
    }

    private void setupListeners() {
        _btnPreviousStep.setOnClickListener(v -> onPreviousStepClick());
        _btnSubmit.setOnClickListener(v -> onStepButtonClick());
    }

    private void onPreviousStepClick() {
        if (!_formLoaded || _currentStepIndex <= 0) {
            return;
        }

        _currentStepIndex--;
        renderCurrentStep();
        _recyclerView.scrollToPosition(0);
    }

    private void onStepButtonClick() {
        if (!_formLoaded) {
            Toast.makeText(this, R.string.offline_toast_config_missing, Toast.LENGTH_SHORT).show();
            return;
        }
        if (_editRecordMissingOrOutdated) {
            Toast.makeText(this, R.string.offline_toast_old_record_edit_not_supported, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!_adapter.validateAll()) {
            Toast.makeText(this, R.string.offline_toast_check_form_input, Toast.LENGTH_SHORT).show();
            // 滚动到第一个错误项
            scrollToFirstError();
            return;
        }

        if (!isLastStep()) {
            _currentStepIndex++;
            renderCurrentStep();
            _recyclerView.scrollToPosition(0);
            return;
        }

        submit();
    }

    private void submit() {
        Map<String, String> formData = collectAllFormData();
        String patternId = _intent.getStringExtra("patternId");
        String schemaVersion = _intent.getStringExtra("schemaVersion");
        OfflineFormRecord record;
        if (_editingRecord == null) {
            record = OfflineFormRecord.createSubmitted(patternId, schemaVersion, formData);
        } else {
            _editingRecord.setValues(formData);
            _editingRecord.setUpdatedAt(System.currentTimeMillis());
            record = _editingRecord;
        }
        // 离线填报数据按表单定义目录保存，删除表单定义时可直接清理对应文件夹。
        OfflineFormFileHelper.writeRecord(this, record);


        Toast.makeText(this, R.string.offline_toast_record_saved, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void renderCurrentStep() {
        if (!_formLoaded) {
            _adapter.setDisplayItems(new ArrayList<>());
            _btnPreviousStep.setVisibility(View.GONE);
            _btnSubmit.setText(R.string.offline_button_save);
            return;
        }

        OfflineFormStep step = _definition.getSteps().get(_currentStepIndex);
        _adapter.setDisplayItems(OfflineFormDefinitionFlattener.flattenStep(step));
        _btnPreviousStep.setVisibility(_currentStepIndex > 0 ? View.VISIBLE : View.GONE);
        _btnSubmit.setText(isLastStep() ? R.string.offline_button_submit : R.string.offline_button_next_step);
        if (step.getTitle() != null && !step.getTitle().isEmpty()) {
            setTitle(step.getTitle());
        }
    }

    private boolean isLastStep() {
        return _definition == null || _definition.getSteps() == null || _currentStepIndex >= _definition.getSteps().size() - 1;
    }

    private Map<String, String> collectAllFormData() {
        Map<String, String> formData = new HashMap<>();
        for (BaseFormItem item : OfflineFormDefinitionFlattener.flattenFields(_definition)) {
            formData.put(item.getId(), item.getValue());
        }
        return formData;
    }

    private void loadEditingRecord(String patternId, String currentSchemaVersion, List<BaseFormItem> formItems) {
        String recordId = _intent.getStringExtra(EXTRA_RECORD_ID);
        if (recordId == null || recordId.isEmpty()) {
            return;
        }

        OfflineFormRecord record = OfflineFormFileHelper.readRecord(this, patternId, recordId);
        if (record == null || !currentSchemaVersion.equals(record.getSchemaVersion())) {
            _editRecordMissingOrOutdated = true;
            Toast.makeText(this, R.string.offline_toast_old_record_edit_not_supported, Toast.LENGTH_SHORT).show();
            return;
        }

        _editingRecord = record;
        applyRecordValues(formItems, record.getValues());
    }

    private void applyRecordValues(List<BaseFormItem> formItems, Map<String, String> values) {
        if (values == null) {
            return;
        }

        for (BaseFormItem formItem : formItems) {
            String value = values.get(formItem.getId());
            if (value == null) {
                continue;
            }
            if (formItem instanceof TextFormItem) {
                ((TextFormItem) formItem).setValue(value);
            } else if (formItem instanceof SelectFormItem) {
                ((SelectFormItem) formItem).setSelectedValue(value);
            }
        }
    }

    private void scrollToFirstError() {
        List<BaseFormItem> items = _adapter.getFormItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getErrorMessage() != null) {
                _recyclerView.scrollToPosition(i);
                break;
            }
        }
    }

}
