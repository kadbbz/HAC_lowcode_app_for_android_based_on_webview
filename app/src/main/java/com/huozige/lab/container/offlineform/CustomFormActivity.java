package com.huozige.lab.container.offlineform;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.formitem.image.ImageCaptureCallback;
import com.huozige.lab.container.offlineform.formitem.image.ImageCaptureHost;
import com.huozige.lab.container.offlineform.formitem.image.OfflineImageFileHelper;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinition;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFlattener;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.OfflineFormDisplayItem;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;
import com.huozige.lab.container.offlineform.model.OfflineFormRecordStatus;
import com.huozige.lab.container.offlineform.model.OfflineFormStep;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.ImageFormItem;
import com.huozige.lab.container.offlineform.model.formitem.PickerFormItem;
import com.huozige.lab.container.offlineform.model.formitem.SelectFormItem;
import com.huozige.lab.container.offlineform.model.formitem.TextFormItem;
import com.huozige.lab.container.proxy.support.capture.CameraViewActivity;
import com.huozige.lab.container.proxy.support.offlinecustomform.FormAdapter;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

public class CustomFormActivity extends AppCompatActivity implements ImageCaptureHost {
    public static final String EXTRA_RECORD_ID = "recordId";
    private static final int MENU_ID_SAVE_RECORD = 1;

    private RecyclerView _recyclerView;
    private TabLayout _stepTabLayout;
    private FormAdapter _adapter;
    private Intent _intent;
    private boolean _formLoaded;
    private boolean _editRecordMissingOrOutdated;
    private boolean _updatingTabs;
    private OfflineFormRecord _editingRecord;
    private OfflineFormDefinition _definition;
    private int _currentStepIndex;
    private ActivityResultLauncher<Intent> _imageCaptureLauncher;
    private ImageFormItem _pendingImageItem;
    private ImageCaptureCallback _pendingImageCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_form_custom_form_activity);

        _intent = getIntent();

        initViews();
        setupRecyclerView();
        registerImageCaptureLauncher();
        loadFormDataFromJson();
        setupListeners();
    }

    private void initViews() {
        _recyclerView = findViewById(R.id.recycler_view);
        _stepTabLayout = findViewById(R.id.tab_steps);
    }

    private void setupRecyclerView() {
        _adapter = new FormAdapter();
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
        _recyclerView.setAdapter(_adapter);
    }

    private void registerImageCaptureLauncher() {
        _imageCaptureLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() != RESULT_OK || result.getData() == null || _pendingImageItem == null || _pendingImageCallback == null) {
                clearPendingImageCapture();
                return;
            }

            String uriText = result.getData().getStringExtra(CameraViewActivity.EXTRA_OUT_URI);
            if (uriText == null || uriText.isEmpty()) {
                clearPendingImageCapture();
                return;
            }

            try {
                OfflineFormRecord draft = ensureDraftRecordForAttachment();
                _pendingImageCallback.onImageCaptured(OfflineImageFileHelper.saveCapturedImage(this, draft.getPatternId(), draft.getRecordId(), _pendingImageItem, Uri.parse(uriText)));
                saveDraftIfNeeded();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "图片保存失败：" + e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                clearPendingImageCapture();
            }
        });
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
        _stepTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (_updatingTabs || !_formLoaded || tab.getPosition() == _currentStepIndex) {
                    return;
                }
                saveDraftIfNeeded();
                _currentStepIndex = tab.getPosition();
                renderCurrentStep();
                _recyclerView.scrollToPosition(0);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_ID_SAVE_RECORD, MENU_ID_SAVE_RECORD, R.string.offline_button_save)
                .setActionView(createSaveActionView())
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_ID_SAVE_RECORD) {
            onSaveMenuClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private View createSaveActionView() {
        LinearLayout actionView = new LinearLayout(this);
        actionView.setOrientation(LinearLayout.HORIZONTAL);
        actionView.setGravity(Gravity.CENTER);
        actionView.setPadding(dp(this, 10), 0, dp(this, 12), 0);
        actionView.setMinimumHeight(dp(this, 48));
        actionView.setClickable(true);
        actionView.setOnClickListener(v -> onSaveMenuClick());

        ImageView iconView = new ImageView(this);
        iconView.setImageResource(R.drawable.ic_offline_save);
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(dp(this, 20), dp(this, 20));
        iconParams.setMargins(0, 0, dp(this, 4), 0);
        actionView.addView(iconView, iconParams);

        TextView textView = new TextView(this);
        textView.setText(R.string.offline_button_save);
        textView.setTextColor(getColor(R.color.white));
        textView.setTextSize(16);
        textView.setGravity(Gravity.CENTER);
        actionView.addView(textView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        return actionView;
    }

    private void onSaveMenuClick() {
        if (!_formLoaded) {
            Toast.makeText(this, R.string.offline_toast_config_missing, Toast.LENGTH_SHORT).show();
            return;
        }
        if (_editRecordMissingOrOutdated) {
            Toast.makeText(this, R.string.offline_toast_old_record_edit_not_supported, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!validateAllSteps()) {
            Toast.makeText(this, R.string.offline_toast_check_form_input, Toast.LENGTH_SHORT).show();
            scrollToFirstError();
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
            _editingRecord.setStatus(OfflineFormRecordStatus.SUBMITTED);
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
            _stepTabLayout.setVisibility(View.GONE);
            return;
        }

        renderStepTabs();
        OfflineFormStep step = _definition.getSteps().get(_currentStepIndex);
        _adapter.setDisplayItems(OfflineFormDefinitionFlattener.flattenStep(step));
        if (step.getTitle() != null && !step.getTitle().isEmpty()) {
            setTitle(step.getTitle());
        }
    }

    private void renderStepTabs() {
        _updatingTabs = true;
        _stepTabLayout.setVisibility(_definition.getSteps().size() > 1 ? View.VISIBLE : View.GONE);
        _stepTabLayout.removeAllTabs();
        for (int i = 0; i < _definition.getSteps().size(); i++) {
            OfflineFormStep step = _definition.getSteps().get(i);
            String title = step.getTitle() == null || step.getTitle().isEmpty() ? "步骤" + (i + 1) : step.getTitle();
            _stepTabLayout.addTab(_stepTabLayout.newTab().setText(title), i == _currentStepIndex);
        }
        _updatingTabs = false;
    }

    private Map<String, String> collectAllFormData() {
        Map<String, String> formData = new HashMap<>();
        for (BaseFormItem item : OfflineFormDefinitionFlattener.flattenFields(_definition)) {
            formData.put(item.getId(), item.getValue());
        }
        return formData;
    }

    private void saveDraftIfNeeded() {
        if (_editingRecord != null && _editingRecord.getStatus() != OfflineFormRecordStatus.DRAFT) {
            return;
        }
        if (!hasFieldBeforeNextStep()) {
            return;
        }

        Map<String, String> formData = collectAllFormData();
        String patternId = _intent.getStringExtra("patternId");
        String schemaVersion = _intent.getStringExtra("schemaVersion");
        if (_editingRecord == null) {
            _editingRecord = OfflineFormRecord.createDraft(patternId, schemaVersion, formData);
        } else {
            _editingRecord.setValues(formData);
            _editingRecord.setStatus(OfflineFormRecordStatus.DRAFT);
            _editingRecord.setUpdatedAt(System.currentTimeMillis());
        }
        OfflineFormFileHelper.writeRecord(this, _editingRecord);
    }

    private boolean validateAllSteps() {
        int originalStepIndex = _currentStepIndex;
        for (int i = 0; i < _definition.getSteps().size(); i++) {
            _currentStepIndex = i;
            renderCurrentStep();
            if (!_adapter.validateAll()) {
                return false;
            }
        }

        _currentStepIndex = originalStepIndex;
        renderCurrentStep();
        return true;
    }

    private OfflineFormRecord ensureDraftRecordForAttachment() {
        if (_editingRecord == null) {
            _editingRecord = OfflineFormRecord.createDraft(
                    _intent.getStringExtra("patternId"),
                    _intent.getStringExtra("schemaVersion"),
                    collectAllFormData());
        } else if (_editingRecord.getStatus() == OfflineFormRecordStatus.DRAFT) {
            _editingRecord.setValues(collectAllFormData());
            _editingRecord.setUpdatedAt(System.currentTimeMillis());
        }
        OfflineFormFileHelper.writeRecord(this, _editingRecord);
        return _editingRecord;
    }

    private boolean hasFieldBeforeNextStep() {
        if (_definition == null || _definition.getSteps() == null) {
            return false;
        }

        int lastStepIndex = Math.min(_currentStepIndex, _definition.getSteps().size() - 1);
        for (int i = 0; i <= lastStepIndex; i++) {
            for (OfflineFormDisplayItem displayItem : OfflineFormDefinitionFlattener.flattenStep(_definition.getSteps().get(i))) {
                if (displayItem.isField()) {
                    return true;
                }
            }
        }
        return false;
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
            } else if (formItem instanceof PickerFormItem) {
                ((PickerFormItem) formItem).setValue(value);
            } else if (formItem instanceof ImageFormItem) {
                ((ImageFormItem) formItem).setValue(value);
            }
        }
    }

    @Override
    public void captureImage(ImageFormItem item, ImageCaptureCallback callback) {
        _pendingImageItem = item;
        _pendingImageCallback = callback;
        Intent cameraIntent = new Intent(this, CameraViewActivity.class);
        cameraIntent.putExtra(CameraViewActivity.EXTRA_OPERATION, CameraViewActivity.OPERATION_TAKE_PHOTO);
        _imageCaptureLauncher.launch(cameraIntent);
    }

    private void clearPendingImageCapture() {
        _pendingImageItem = null;
        _pendingImageCallback = null;
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
