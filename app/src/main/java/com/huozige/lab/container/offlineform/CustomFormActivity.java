package com.huozige.lab.container.offlineform;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.formitem.AttachmentCallback;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemValueHelper;
import com.huozige.lab.container.offlineform.formitem.file.FileUploadHost;
import com.huozige.lab.container.offlineform.formitem.file.OfflineFileHelper;
import com.huozige.lab.container.offlineform.formitem.image.ImageCaptureHost;
import com.huozige.lab.container.offlineform.formitem.image.OfflineImagePreviewActivity;
import com.huozige.lab.container.offlineform.formitem.image.OfflineImageFileHelper;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinition;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFlattener;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.OfflineFormDisplayItem;
import com.huozige.lab.container.offlineform.model.OfflineFormNode;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;
import com.huozige.lab.container.offlineform.model.OfflineFormRecordStatus;
import com.huozige.lab.container.offlineform.model.OfflineFormStep;
import com.huozige.lab.container.offlineform.model.formitem.common.AttachmentFormItemValue;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.file.FileFormItem;
import com.huozige.lab.container.offlineform.model.formitem.image.ImageFormItem;
import com.huozige.lab.container.offlineform.model.formitem.picker.PickerFormItem;
import com.huozige.lab.container.offlineform.model.formitem.select.SelectFormItem;
import com.huozige.lab.container.offlineform.model.formitem.text.TextFormItem;
import com.huozige.lab.container.proxy.support.capture.CameraViewActivity;
import com.huozige.lab.container.proxy.support.offlinecustomform.FormAdapter;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

public class CustomFormActivity extends AppCompatActivity implements ImageCaptureHost, FileUploadHost {
    public static final String EXTRA_RECORD_ID = "recordId";
    private static final int MENU_ID_SAVE_RECORD = 1;
    private static final int FILTER_ALL = 0;
    private static final int FILTER_UNFILLED = 1;
    private static final int FILTER_FILLED = 2;
    private static final int FILTER_SEARCH = 3;

    private RecyclerView _recyclerView;
    private NestedScrollView _formScrollView;
    private TabLayout _stepTabLayout;
    private LinearLayout _itemNavigationLayout;
    private LinearLayout _searchKeywordLayout;
    private TextView _previousItemButton;
    private TextView _nextItemButton;
    private TextView _bottomHintTextView;
    private TextView _searchKeywordTextView;
    private ImageView _clearSearchButton;
    private Spinner _filterModeSpinner;
    private FormAdapter _adapter;
    private Intent _intent;
    private boolean _formLoaded;
    private boolean _editRecordMissingOrOutdated;
    private boolean _updatingTabs;
    private AdapterView.OnItemSelectedListener _filterModeSelectedListener;
    private int _filterMode = FILTER_ALL;
    private int _filterModeBeforeSearch = FILTER_ALL;
    private String _searchKeyword = "";
    private OfflineFormRecord _editingRecord;
    private OfflineFormDefinition _definition;
    private int _currentStepIndex;
    private ActivityResultLauncher<Intent> _imageCaptureLauncher;
    private ActivityResultLauncher<String> _imageUploadLauncher;
    private ActivityResultLauncher<Intent> _imagePreviewLauncher;
    private ActivityResultLauncher<String[]> _fileUploadLauncher;
    private ImageFormItem _pendingImageItem;
    private AttachmentCallback _pendingImageCallback;
    private FileFormItem _pendingFileItem;
    private AttachmentCallback _pendingFileCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_form_custom_form_activity);

        _intent = getIntent();

        initViews();
        setupRecyclerView();
        registerImageCaptureLauncher();
        registerImageUploadLauncher();
        registerImagePreviewLauncher();
        registerFileUploadLauncher();
        loadFormDataFromJson();
        setupListeners();
    }

    private void initViews() {
        _recyclerView = findViewById(R.id.recycler_view);
        _formScrollView = findViewById(R.id.scroll_form);
        _stepTabLayout = findViewById(R.id.tab_steps);
        _itemNavigationLayout = findViewById(R.id.layout_item_navigation);
        _searchKeywordLayout = findViewById(R.id.layout_search_keyword);
        _previousItemButton = findViewById(R.id.button_previous_item);
        _nextItemButton = findViewById(R.id.button_next_item);
        _bottomHintTextView = findViewById(R.id.text_form_bottom_hint);
        _searchKeywordTextView = findViewById(R.id.text_search_keyword);
        _clearSearchButton = findViewById(R.id.button_clear_search);
        _filterModeSpinner = findViewById(R.id.spinner_filter_mode);
        setupClearSearchButtonBackground();
        setupBottomHintHeight();
    }

    private void setupClearSearchButtonBackground() {
        GradientDrawable background = new GradientDrawable();
        background.setShape(GradientDrawable.OVAL);
        background.setColor(getColor(R.color.gray));
        _clearSearchButton.setBackground(background);
    }

    private void setupRecyclerView() {
        _adapter = new FormAdapter();
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
        _recyclerView.setAdapter(_adapter);
    }

    private void setupBottomHintHeight() {
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.35f);
        ViewGroup.LayoutParams params = _bottomHintTextView.getLayoutParams();
        params.height = height;
        _bottomHintTextView.setLayoutParams(params);
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
                List<AttachmentFormItemValue> images = new ArrayList<>();
                images.add(OfflineImageFileHelper.saveImage(this, draft.getPatternId(), _pendingImageItem, Uri.parse(uriText)));
                _pendingImageCallback.onAttachmentsSelected(images);
                saveDraftIfNeeded();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, getString(R.string.offline_toast_image_save_failed, e.getMessage()), Toast.LENGTH_LONG).show();
            } finally {
                clearPendingImageCapture();
            }
        });
    }

    private void registerImageUploadLauncher() {
        _imageUploadLauncher = registerForActivityResult(new ActivityResultContracts.GetMultipleContents(), uris -> {
            if (uris == null || uris.isEmpty() || _pendingImageItem == null || _pendingImageCallback == null) {
                clearPendingImageCapture();
                return;
            }

            try {
                OfflineFormRecord draft = ensureDraftRecordForAttachment();
                List<AttachmentFormItemValue> images = new ArrayList<>();
                int remainingCount = getRemainingImageCount(_pendingImageItem);
                for (Uri uri : uris) {
                    if (images.size() >= remainingCount) {
                        break;
                    }
                    images.add(OfflineImageFileHelper.saveImage(this, draft.getPatternId(), _pendingImageItem, uri));
                }
                _pendingImageCallback.onAttachmentsSelected(images);
                if (uris.size() > images.size()) {
                    Toast.makeText(this, getString(R.string.offline_toast_image_max_count_reached, _pendingImageItem.getMaxCount()), Toast.LENGTH_SHORT).show();
                }
                saveDraftIfNeeded();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, getString(R.string.offline_toast_image_save_failed, e.getMessage()), Toast.LENGTH_LONG).show();
            } finally {
                clearPendingImageCapture();
            }
        });
    }

    private void registerImagePreviewLauncher() {
        _imagePreviewLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (_pendingImageItem == null || result.getResultCode() != RESULT_OK || result.getData() == null) {
                _pendingImageItem = null;
                return;
            }
            ArrayList<String> fileNames = result.getData().getStringArrayListExtra(OfflineImagePreviewActivity.EXTRA_FILE_NAMES);
            if (fileNames == null) {
                _pendingImageItem = null;
                return;
            }
            _pendingImageItem.setImagesFromFileNames(fileNames);
            saveDraftIfNeeded();
            _adapter.notifyDataSetChanged();
            _pendingImageItem = null;
        });
    }

    private void registerFileUploadLauncher() {
        _fileUploadLauncher = registerForActivityResult(new ActivityResultContracts.OpenMultipleDocuments(), uris -> {
            if (uris == null || uris.isEmpty() || _pendingFileItem == null || _pendingFileCallback == null) {
                clearPendingFileUpload();
                return;
            }

            try {
                OfflineFormRecord draft = ensureDraftRecordForAttachment();
                List<AttachmentFormItemValue> files = new ArrayList<>();
                int remainingCount = getRemainingFileCount(_pendingFileItem);
                for (Uri uri : uris) {
                    if (files.size() >= remainingCount) {
                        break;
                    }
                    String originalName = OfflineFileHelper.getDisplayName(this, uri);
                    if (_pendingFileItem.containsOriginalName(originalName) || containsOriginalName(files, originalName)) {
                        Toast.makeText(this, getString(R.string.offline_toast_duplicate_file, originalName), Toast.LENGTH_SHORT).show();
                        continue;
                    }
                    files.add(OfflineFileHelper.saveFile(this, draft.getPatternId(), _pendingFileItem, uri));
                }
                _pendingFileCallback.onAttachmentsSelected(files);
                if (uris.size() > files.size()) {
                    Toast.makeText(this, R.string.offline_toast_file_upload_partial, Toast.LENGTH_SHORT).show();
                }
                saveDraftIfNeeded();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, getString(R.string.offline_toast_file_save_failed, e.getMessage()), Toast.LENGTH_LONG).show();
            } finally {
                clearPendingFileUpload();
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
                List<BaseFormItem> formItems = OfflineFormDefinitionFlattener.flattenFields(_definition);
                applyPatternIdToAttachmentItems(formItems, patternId);
                loadEditingRecord(patternId, _definition.getSchemaVersion(), formItems);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.offline_toast_parse_form_failed, e.getMessage()), Toast.LENGTH_LONG).show();
        }
        _formLoaded = _definition != null && _definition.getSteps() != null && !_definition.getSteps().isEmpty();
        renderCurrentStep();
    }

    private void setupListeners() {
        _previousItemButton.setOnClickListener(v -> navigateRootItem(-1));
        _nextItemButton.setOnClickListener(v -> navigateRootItem(1));
        _searchKeywordLayout.setOnClickListener(v -> clearSearchFilter());
        setupFilterModeSpinner();
        _stepTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (_updatingTabs || !_formLoaded || tab.getPosition() == _currentStepIndex) {
                    return;
                }
                saveDraftIfNeeded();
                _currentStepIndex = tab.getPosition();
                resetFilterMode();
                renderCurrentStep();
                _recyclerView.scrollToPosition(0);
                _formScrollView.scrollTo(0, 0);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setupFilterModeSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{
                getString(R.string.offline_filter_all),
                getString(R.string.offline_filter_unfilled),
                getString(R.string.offline_filter_filled),
                getString(R.string.offline_filter_search)
        });
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _filterModeSpinner.setAdapter(adapter);
        _filterModeSpinner.setSelection(FILTER_ALL);
        _filterModeSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == FILTER_SEARCH) {
                    if (_filterMode != FILTER_SEARCH) {
                        _filterModeBeforeSearch = _filterMode;
                    }
                    showSearchDialog();
                    return;
                }
                _searchKeyword = "";
                _filterMode = position;
                renderCurrentStep();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        _filterModeSpinner.setOnItemSelectedListener(_filterModeSelectedListener);
    }

    private void resetFilterMode() {
        _filterModeSpinner.setOnItemSelectedListener(null);
        _filterMode = FILTER_ALL;
        _filterModeBeforeSearch = FILTER_ALL;
        _searchKeyword = "";
        _filterModeSpinner.setSelection(FILTER_ALL);
        _filterModeSpinner.setOnItemSelectedListener(_filterModeSelectedListener);
    }

    private void showSearchDialog() {
        LinearLayout container = new LinearLayout(this);
        container.setPadding(dp(this, 24), 0, dp(this, 24), 0);

        EditText input = new EditText(this);
        input.setSingleLine(true);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint(R.string.offline_hint_field_search);
        input.setText(_searchKeyword);
        input.setSelectAllOnFocus(true);
        container.addView(input, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        new AlertDialog.Builder(this)
                .setTitle(R.string.offline_dialog_search_title)
                .setView(container)
                .setPositiveButton(R.string.offline_button_confirm, (dialog, which) -> applySearchFilter(input.getText().toString()))
                .setNegativeButton(R.string.offline_button_cancel, (dialog, which) -> restoreFilterModeBeforeSearch())
                .setOnCancelListener(dialog -> restoreFilterModeBeforeSearch())
                .show();
    }

    private void applySearchFilter(String keyword) {
        _searchKeyword = keyword == null ? "" : keyword.trim();
        if (_searchKeyword.isEmpty()) {
            clearSearchFilter();
            return;
        }
        _filterModeBeforeSearch = _filterMode == FILTER_SEARCH ? _filterModeBeforeSearch : _filterMode;
        _filterMode = FILTER_SEARCH;
        setFilterModeSelection(FILTER_SEARCH);
        renderCurrentStep();
    }

    private void clearSearchFilter() {
        _searchKeyword = "";
        _filterMode = FILTER_ALL;
        _filterModeBeforeSearch = FILTER_ALL;
        setFilterModeSelection(FILTER_ALL);
        renderCurrentStep();
    }

    private void restoreFilterModeBeforeSearch() {
        _filterMode = _filterModeBeforeSearch;
        setFilterModeSelection(_filterMode);
    }

    private void setFilterModeSelection(int filterMode) {
        _filterModeSpinner.setOnItemSelectedListener(null);
        _filterModeSpinner.setSelection(filterMode);
        _filterModeSpinner.setOnItemSelectedListener(_filterModeSelectedListener);
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
            _itemNavigationLayout.setVisibility(View.GONE);
            return;
        }

        renderStepTabs();
        renderSearchKeyword();
        OfflineFormStep step = _definition.getSteps().get(_currentStepIndex);
        _adapter.setDisplayItems(buildCurrentStepDisplayItems(step));
        updateItemNavigationVisibility();
        if (step.getTitle() != null && !step.getTitle().isEmpty()) {
            setTitle(step.getTitle());
        }
    }

    private List<OfflineFormDisplayItem> buildCurrentStepDisplayItems(OfflineFormStep step) {
        List<OfflineFormDisplayItem> displayItems = OfflineFormDefinitionFlattener.flattenStep(step);
        if (_filterMode == FILTER_ALL) {
            return displayItems;
        }

        List<OfflineFormDisplayItem> result = new ArrayList<>();
        int skipDepth = -1;
        for (OfflineFormDisplayItem item : displayItems) {
            if (skipDepth >= 0) {
                if (item.getDepth() > skipDepth) {
                    continue;
                }
                skipDepth = -1;
            }

            if (item.getDepth() != 0) {
                result.add(item);
                continue;
            }
            if (shouldShowRootItemByFilter(item)) {
                result.add(item);
            } else if (item.isGroup()) {
                skipDepth = item.getDepth();
            }
        }
        return result;
    }

    private boolean shouldShowRootItemByFilter(OfflineFormDisplayItem item) {
        switch (_filterMode) {
            case FILTER_UNFILLED:
                return item.isField()
                        ? item.getFormItem().isEmpty()
                        : item.isGroup() && hasEmptyField(item.getNode());
            case FILTER_FILLED:
                return item.isField()
                        ? !item.getFormItem().isEmpty()
                        : item.isGroup() && hasField(item.getNode()) && !hasEmptyField(item.getNode());
            case FILTER_SEARCH:
                return matchesSearch(item);
            case FILTER_ALL:
            default:
                return true;
        }
    }

    private void renderSearchKeyword() {
        boolean searching = _filterMode == FILTER_SEARCH && !_searchKeyword.isEmpty();
        _searchKeywordLayout.setVisibility(searching ? View.VISIBLE : View.GONE);
        _filterModeSpinner.setVisibility(searching ? View.GONE : View.VISIBLE);
        if (searching) {
            _searchKeywordTextView.setText(getString(R.string.offline_text_search_keyword, _searchKeyword));
        }
    }

    private boolean matchesSearch(OfflineFormDisplayItem item) {
        if (_searchKeyword.isEmpty()) {
            return true;
        }
        if (item.isField()) {
            return containsSearchText(item.getNode()) || containsSearchText(item.getFormItem().getTitle());
        }
        return containsSearchText(item.getNode()) || hasSearchText(item.getNode());
    }

    private boolean hasSearchText(OfflineFormNode node) {
        if (node == null || node.getChildren() == null) {
            return false;
        }
        for (OfflineFormNode child : node.getChildren()) {
            if (containsSearchText(child)) {
                return true;
            }
            if (child.getField() != null && containsSearchText(child.getField().getTitle())) {
                return true;
            }
            if (hasSearchText(child)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsSearchText(OfflineFormNode node) {
        if (node == null) {
            return false;
        }
        return containsSearchText(node.getTitle()) || containsSearchText(node.getContent());
    }

    private boolean containsSearchText(String text) {
        return text != null && text.toLowerCase(Locale.CHINA).contains(_searchKeyword.toLowerCase(Locale.CHINA));
    }

    private boolean hasEmptyField(OfflineFormNode node) {
        if (node == null || node.getChildren() == null) {
            return false;
        }
        for (OfflineFormNode child : node.getChildren()) {
            if (OfflineFormNode.TYPE_FIELD.equals(child.getNodeType()) && child.getField() != null && child.getField().isEmpty()) {
                return true;
            }
            if (OfflineFormNode.TYPE_GROUP.equals(child.getNodeType()) && hasEmptyField(child)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasField(OfflineFormNode node) {
        if (node == null || node.getChildren() == null) {
            return false;
        }
        for (OfflineFormNode child : node.getChildren()) {
            if (OfflineFormNode.TYPE_FIELD.equals(child.getNodeType())) {
                return true;
            }
            if (OfflineFormNode.TYPE_GROUP.equals(child.getNodeType()) && hasField(child)) {
                return true;
            }
        }
        return false;
    }

    private void navigateRootItem(int direction) {
        int targetPosition = findRootItemPosition(direction);
        if (targetPosition < 0) {
            return;
        }
        scrollToRootItem(targetPosition);
    }

    private int findRootItemPosition(int direction) {
        int itemCount = _adapter.getItemCount();
        int anchorY = getCurrentContentScrollY() + dp(this, direction > 0 ? 16 : -16);
        int start = direction > 0 ? 0 : itemCount - 1;
        int end = direction > 0 ? itemCount : -1;
        for (int i = start; i != end; i += direction > 0 ? 1 : -1) {
            View itemView = getRootItemView(i);
            if (itemView != null && (direction > 0 ? itemView.getTop() > anchorY : itemView.getTop() < anchorY)) {
                return i;
            }
        }
        return -1;
    }

    private int getCurrentContentScrollY() {
        return _formScrollView.getScrollY() - _recyclerView.getTop();
    }

    private void scrollToRootItem(int position) {
        _recyclerView.scrollToPosition(position);
        _recyclerView.postDelayed(() -> {
            View itemView = getRootItemView(position);
            if (itemView == null) {
                return;
            }
            int scrollY = Math.max(0, _recyclerView.getTop() + itemView.getTop() - dp(this, 8));
            _formScrollView.smoothScrollTo(0, scrollY);
            playHighlight(itemView);
        }, 80);
    }

    private View getRootItemView(int position) {
        RecyclerView.ViewHolder viewHolder = _recyclerView.findViewHolderForAdapterPosition(position);
        return viewHolder == null ? null : viewHolder.itemView;
    }

    private void updateItemNavigationVisibility() {
        int itemCount = _adapter == null ? 0 : _adapter.getItemCount();
        _itemNavigationLayout.setVisibility(_formLoaded && itemCount > 1 ? View.VISIBLE : View.GONE);
    }

    private void playHighlight(View itemView) {
        Drawable originalForeground = itemView.getForeground();
        ColorDrawable highlight = new ColorDrawable(getColor(R.color.huozige_blue));
        itemView.setForeground(highlight);

        ValueAnimator animator = ValueAnimator.ofInt(72, 0);
        animator.setDuration(650);
        animator.addUpdateListener(animation -> highlight.setAlpha((int) animation.getAnimatedValue()));
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                itemView.setForeground(originalForeground);
            }
        });
        animator.start();
    }

    private void renderStepTabs() {
        _updatingTabs = true;
        _stepTabLayout.setVisibility(_definition.getSteps().size() > 1 ? View.VISIBLE : View.GONE);
        _stepTabLayout.removeAllTabs();
        for (int i = 0; i < _definition.getSteps().size(); i++) {
            OfflineFormStep step = _definition.getSteps().get(i);
            String title = step.getTitle() == null || step.getTitle().isEmpty() ? getString(R.string.offline_text_default_step, i + 1) : step.getTitle();
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
        int originalFilterMode = _filterMode;
        String originalSearchKeyword = _searchKeyword;
        _filterMode = FILTER_ALL;
        _searchKeyword = "";
        clearErrorsForEmptyValidationUnits();
        for (int i = 0; i < _definition.getSteps().size(); i++) {
            OfflineFormStep step = _definition.getSteps().get(i);
            boolean rendered = false;
            for (List<OfflineFormDisplayItem> validationUnit : collectValidationUnits(step)) {
                if (!hasFilledField(validationUnit)) {
                    continue;
                }
                if (!rendered) {
                    _currentStepIndex = i;
                    renderCurrentStep();
                    rendered = true;
                }
                if (!_adapter.validateItems(validationUnit)) {
                    _searchKeyword = "";
                    setFilterModeSelection(FILTER_ALL);
                    return false;
                }
            }
        }

        _currentStepIndex = originalStepIndex;
        _filterMode = originalFilterMode;
        _searchKeyword = originalSearchKeyword;
        setFilterModeSelection(_filterMode);
        renderCurrentStep();
        return true;
    }

    private List<List<OfflineFormDisplayItem>> collectValidationUnits(OfflineFormStep step) {
        List<OfflineFormDisplayItem> displayItems = OfflineFormDefinitionFlattener.flattenStep(step);
        List<List<OfflineFormDisplayItem>> validationUnits = new ArrayList<>();
        for (int i = 0; i < displayItems.size(); i++) {
            OfflineFormDisplayItem displayItem = displayItems.get(i);
            if (displayItem.getDepth() != 0) {
                continue;
            }
            if (displayItem.isGroup()) {
                List<OfflineFormDisplayItem> groupItems = new ArrayList<>();
                groupItems.add(displayItem);
                int nextIndex = i + 1;
                while (nextIndex < displayItems.size() && displayItems.get(nextIndex).getDepth() > 0) {
                    groupItems.add(displayItems.get(nextIndex));
                    nextIndex++;
                }
                validationUnits.add(groupItems);
                i = nextIndex - 1;
            } else if (displayItem.isField()) {
                List<OfflineFormDisplayItem> fieldItem = new ArrayList<>();
                fieldItem.add(displayItem);
                validationUnits.add(fieldItem);
            }
        }
        return validationUnits;
    }

    private void clearErrorsForEmptyValidationUnits() {
        for (OfflineFormStep step : _definition.getSteps()) {
            for (List<OfflineFormDisplayItem> validationUnit : collectValidationUnits(step)) {
                if (!hasFilledField(validationUnit)) {
                    clearValidationErrors(validationUnit);
                }
            }
        }
    }

    private boolean hasFilledField(List<OfflineFormDisplayItem> displayItems) {
        for (OfflineFormDisplayItem displayItem : displayItems) {
            if (displayItem.isField() && !displayItem.getFormItem().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void clearValidationErrors(List<OfflineFormDisplayItem> displayItems) {
        for (OfflineFormDisplayItem displayItem : displayItems) {
            if (displayItem.isField()) {
                displayItem.getFormItem().clearError();
            }
        }
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

    private void applyPatternIdToAttachmentItems(List<BaseFormItem> formItems, String patternId) {
        OfflineFormItemValueHelper.applyPatternId(formItems, patternId);
    }

    private void applyRecordValues(List<BaseFormItem> formItems, Map<String, String> values) {
        OfflineFormItemValueHelper.applyValues(formItems, values);
    }

    @Override
    public void captureImage(ImageFormItem item, AttachmentCallback callback) {
        _pendingImageItem = item;
        _pendingImageCallback = callback;
        Intent cameraIntent = new Intent(this, CameraViewActivity.class);
        cameraIntent.putExtra(CameraViewActivity.EXTRA_OPERATION, CameraViewActivity.OPERATION_TAKE_PHOTO);
        cameraIntent.putStringArrayListExtra(CameraViewActivity.EXTRA_WATERMARK_LINES, OfflineImageFileHelper.buildWatermarkLines(item));
        cameraIntent.putExtra(CameraViewActivity.EXTRA_CONFIRM_PHOTO, true);
        _imageCaptureLauncher.launch(cameraIntent);
    }

    @Override
    public void uploadImage(ImageFormItem item, AttachmentCallback callback) {
        _pendingImageItem = item;
        _pendingImageCallback = callback;
        _imageUploadLauncher.launch("image/*");
    }

    @Override
    public void previewImages(ImageFormItem item, ArrayList<String> fileNames, int index) {
        _pendingImageItem = item;
        _imagePreviewLauncher.launch(OfflineImagePreviewActivity.createIntent(this, item.getPatternId(), fileNames, index));
    }

    @Override
    public void onImagesChanged(ImageFormItem item) {
        saveDraftIfNeeded();
    }

    @Override
    public void uploadFile(FileFormItem item, AttachmentCallback callback) {
        _pendingFileItem = item;
        _pendingFileCallback = callback;
        _fileUploadLauncher.launch(OfflineFileHelper.buildMimeTypes(item.getFileItemConfig()));
    }

    @Override
    public void onFilesChanged(FileFormItem item) {
        saveDraftIfNeeded();
    }

    private int getRemainingImageCount(ImageFormItem item) {
        if (item.getMaxCount() <= 0) {
            return Integer.MAX_VALUE;
        }
        return Math.max(0, item.getMaxCount() - item.getImages().size());
    }

    private int getRemainingFileCount(FileFormItem item) {
        int maxCount = item.getFileItemConfig() == null ? 0 : item.getFileItemConfig().getMaxCount();
        if (maxCount <= 0) {
            return Integer.MAX_VALUE;
        }
        return Math.max(0, maxCount - item.getFiles().size());
    }

    private boolean containsOriginalName(List<AttachmentFormItemValue> files, String originalName) {
        if (files == null) {
            return false;
        }
        for (AttachmentFormItemValue file : files) {
            if (file != null && originalName.equals(file.getOriginalName())) {
                return true;
            }
        }
        return false;
    }

    private void clearPendingImageCapture() {
        _pendingImageItem = null;
        _pendingImageCallback = null;
    }

    private void clearPendingFileUpload() {
        _pendingFileItem = null;
        _pendingFileCallback = null;
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
