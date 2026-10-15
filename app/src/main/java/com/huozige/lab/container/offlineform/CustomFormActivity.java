package com.huozige.lab.container.offlineform;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.OnBackPressedCallback;
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
import com.huozige.lab.container.offlineform.formitem.signature.SignatureCallback;
import com.huozige.lab.container.offlineform.formitem.signature.SignatureCaptureActivity;
import com.huozige.lab.container.offlineform.formitem.signature.SignatureCaptureHost;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinition;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFlattener;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.OfflineFormDisplayItem;
import com.huozige.lab.container.offlineform.model.OfflineFormNode;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;
import com.huozige.lab.container.offlineform.model.OfflineFormRecordStatus;
import com.huozige.lab.container.offlineform.model.OfflineFormStep;
import com.huozige.lab.container.offlineform.model.OfflineFormValidator;
import com.huozige.lab.container.offlineform.model.formitem.common.AttachmentFormItemValue;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.file.FileFormItem;
import com.huozige.lab.container.offlineform.model.formitem.image.ImageFormItem;
import com.huozige.lab.container.offlineform.model.formitem.picker.PickerFormItem;
import com.huozige.lab.container.offlineform.model.formitem.select.SelectFormItem;
import com.huozige.lab.container.offlineform.model.formitem.signature.SignatureFormItem;
import com.huozige.lab.container.offlineform.model.formitem.signature.SignatureFormItemValue;
import com.huozige.lab.container.offlineform.model.formitem.text.TextFormItem;
import com.huozige.lab.container.offlineform.util.OfflineFormFieldTimes;
import com.huozige.lab.container.offlineform.util.Utils;
import com.huozige.lab.container.proxy.support.capture.CameraViewActivity;
import com.huozige.lab.container.proxy.support.offlinecustomform.FormAdapter;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;
import com.huozige.lab.container.proxy.support.pdf.PDFPreviewActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

public class CustomFormActivity extends AppCompatActivity implements ImageCaptureHost, FileUploadHost, SignatureCaptureHost {
    public static final String EXTRA_RECORD_ID = "recordId";
    private static final String STATE_RECORD_ID = "offline_form_record_id";
    private static final String STATE_CURRENT_STEP_INDEX = "offline_form_current_step_index";
    private static final String STATE_FILTER_MODE = "offline_form_filter_mode";
    private static final String STATE_SEARCH_KEYWORD = "offline_form_search_keyword";
    private static final int MENU_ID_SAVE_RECORD = 1;
    private static final int FILTER_ALL = 0;
    private static final int FILTER_UNFILLED = 1;
    private static final int FILTER_FILLED = 2;
    private static final int FILTER_SEARCH = 3;
    // 字段变更后延迟落盘，避免逐字符写文件。
    private static final long AUTO_SAVE_DELAY_MS = 600;
    // 字段高亮闪光的持续时间（“仅未填”筛选与“跳转首个未填必填项”共用）。
    private static final long FIELD_HIGHLIGHT_MS = 1000;

    private RecyclerView _recyclerView;
    private NestedScrollView _formScrollView;
    private TabLayout _stepTabLayout;
    private ImageView _stepScrollLeftHint;
    private ImageView _stepScrollRightHint;
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
    // 上次落盘的"路径→值"快照，用于字段变更检测（仅进程内使用）。
    private Map<String, String> _lastPersistedSnapshot = new HashMap<>();
    private OfflineFormDefinition _definition;
    private int _currentStepIndex;
    private ActivityResultLauncher<Intent> _imageCaptureLauncher;
    private ActivityResultLauncher<String> _imageUploadLauncher;
    private ActivityResultLauncher<Intent> _imagePreviewLauncher;
    private ActivityResultLauncher<String[]> _fileUploadLauncher;
    private ActivityResultLauncher<Intent> _signatureCaptureLauncher;
    private ImageFormItem _pendingImageItem;
    private AttachmentCallback _pendingImageCallback;
    private FileFormItem _pendingFileItem;
    private AttachmentCallback _pendingFileCallback;
    private SignatureFormItem _pendingSignatureItem;
    private String _pendingSignatureUserName;
    private SignatureCallback _pendingSignatureCallback;
    // 右下角悬浮快捷按钮组。
    private OfflineQuickActionView _quickActions;
    // 字段变更后的防抖落盘。
    private final Handler _autoSaveHandler = new Handler(Looper.getMainLooper());
    private final Runnable _autoSaveRunnable = this::persistRecord;
    // 需要高亮的字段 id 集合与失效时间戳；到期后清除高亮。
    private final List<String> _highlightFieldIds = new ArrayList<>();
    private long _highlightUntil;
    private final Runnable _clearHighlightRunnable = this::clearFieldHighlight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_form_custom_form_activity);

        _intent = getIntent();

        // 如果 Activity 因系统原因被重建，优先恢复本次会话刚刚自动保存的草稿。
        if (savedInstanceState != null) {
            String savedRecordId = savedInstanceState.getString(STATE_RECORD_ID);
            if (savedRecordId != null && !savedRecordId.isEmpty()) {
                _intent.putExtra(EXTRA_RECORD_ID, savedRecordId);
            }
        }

        initViews();
        setupRecyclerView();
        setupQuickActions();
        registerImageCaptureLauncher();
        registerImageUploadLauncher();
        registerImagePreviewLauncher();
        registerFileUploadLauncher();
        registerSignatureCaptureLauncher();
        loadFormDataFromJson();
        setupListeners();
        registerBackHandler();
        restoreUiState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        // 旋转、切后台、启动拍照/文件选择等场景都会先进入 onPause，提前落盘可避免只依赖显式“保存”。
        _autoSaveHandler.removeCallbacks(_autoSaveRunnable);
        persistRecord();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        _autoSaveHandler.removeCallbacks(_autoSaveRunnable);
        _autoSaveHandler.removeCallbacks(_clearHighlightRunnable);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        persistRecord();
        if (_editingRecord != null) {
            outState.putString(STATE_RECORD_ID, _editingRecord.getRecordId());
        }
        outState.putInt(STATE_CURRENT_STEP_INDEX, _currentStepIndex);
        outState.putInt(STATE_FILTER_MODE, _filterMode);
        outState.putString(STATE_SEARCH_KEYWORD, _searchKeyword);
        super.onSaveInstanceState(outState);
    }

    private void restoreUiState(Bundle savedInstanceState) {
        if (savedInstanceState == null || !_formLoaded || _definition == null
                || _definition.getSteps() == null || _definition.getSteps().isEmpty()) {
            return;
        }

        _currentStepIndex = Math.max(0, Math.min(
                savedInstanceState.getInt(STATE_CURRENT_STEP_INDEX, 0),
                _definition.getSteps().size() - 1));
        _filterMode = savedInstanceState.getInt(STATE_FILTER_MODE, FILTER_ALL);
        if (_filterMode < FILTER_ALL || _filterMode > FILTER_FILLED) {
            _filterMode = FILTER_ALL;
        }
        _searchKeyword = savedInstanceState.getString(STATE_SEARCH_KEYWORD, "");
        setFilterModeSelection(_filterMode);
        renderCurrentStep();
        if (_filterMode == FILTER_UNFILLED) {
            startRequiredEmptyHighlight();
        }
    }

    private void initViews() {
        _recyclerView = findViewById(R.id.recycler_view);
        _formScrollView = findViewById(R.id.scroll_form);
        _stepTabLayout = findViewById(R.id.tab_steps);
        _stepScrollLeftHint = findViewById(R.id.hint_step_scroll_left);
        _stepScrollRightHint = findViewById(R.id.hint_step_scroll_right);
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
        String patternId = _intent.getStringExtra("patternId");
        List<File> manualFiles = OfflineFormFileHelper.getManualFiles(this, patternId);
        List<String> manualFileNames = new ArrayList<>();
        for (File manualFile : manualFiles) {
            manualFileNames.add(manualFile.getName());
        }
        _adapter.setDocumentFileNames(manualFileNames);
        _adapter.setOnDocumentClickListener((item, fileName) -> openManualPdf(fileName));
        _adapter.setFieldUpdateTimesProvider(() -> _editingRecord == null
                ? Collections.emptyMap()
                : _editingRecord.getFieldUpdateTimes());
        _adapter.setOnFieldChangedListener(this::scheduleAutoSave);
        _adapter.setLockedFieldIdsProvider(() -> _editingRecord == null
                ? null
                : _editingRecord.getLockedFieldIds());
        _adapter.setOnFieldLockedListener(this::lockField);
        _adapter.setHighlightedFieldIdsProvider(() ->
                System.currentTimeMillis() < _highlightUntil ? new ArrayList<>(_highlightFieldIds) : null);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
        _recyclerView.setAdapter(_adapter);
    }

    /** 装配右下角悬浮快捷按钮组：回到顶部、跳转首个未填必填项、折叠所有分组。 */
    private void setupQuickActions() {
        ViewGroup content = findViewById(android.R.id.content);
        _quickActions = new OfflineQuickActionView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                _quickActions.getPreferredWidth(), _quickActions.getPreferredHeight());
        params.gravity = Gravity.END | Gravity.BOTTOM;
        params.rightMargin = dp(this, 16);
        params.bottomMargin = dp(this, 72);
        content.addView(_quickActions, params);
        _quickActions.setActionListener(new OfflineQuickActionView.ActionListener() {
            @Override
            public void onScrollToTop() {
                _formScrollView.smoothScrollTo(0, 0);
            }

            @Override
            public void onJumpToFirstRequiredEmpty() {
                jumpToFirstRequiredEmpty();
            }

            @Override
            public void onCollapseAllGroups() {
                if (_adapter != null) {
                    _adapter.collapseAllGroups();
                }
            }
        });
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
                OfflineFormRecord draft = ensureRecordForAttachment();
                List<AttachmentFormItemValue> images = new ArrayList<>();
                images.add(OfflineImageFileHelper.saveImage(this, draft.getPatternId(), _pendingImageItem, Uri.parse(uriText)));
                _pendingImageCallback.onAttachmentsSelected(images);
                persistRecord();
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
                OfflineFormRecord draft = ensureRecordForAttachment();
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
                persistRecord();
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
            persistRecord();
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
                OfflineFormRecord draft = ensureRecordForAttachment();
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
                persistRecord();
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
        // 以"装载后的值"作为字段变更检测基准：装载初值不算变更。
        _lastPersistedSnapshot = OfflineFormFieldTimes.collectValues(_definition);
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
                persistRecord();
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
        _stepTabLayout.setOnScrollChangeListener((view, scrollX, scrollY, oldScrollX, oldScrollY) -> updateStepScrollHints());
        _stepScrollLeftHint.setOnClickListener(v -> Toast.makeText(
                this, R.string.offline_hint_step_swipe_right, Toast.LENGTH_SHORT).show());
        _stepScrollRightHint.setOnClickListener(v -> Toast.makeText(
                this, R.string.offline_hint_step_swipe_left, Toast.LENGTH_SHORT).show());
    }

    private void registerBackHandler() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                onBackRequested();
            }
        });
    }

    /**
     * 返回 = 一次收尾：校验 + 落盘 + （未通过时）轻提示，然后退出。
     * 通过 -> 待上传；不通过 -> 草稿。不阻塞退出，不丢数据。
     */
    private void onBackRequested() {
        if (!_formLoaded || _definition == null || _editRecordMissingOrOutdated) {
            finish();
            return;
        }
        OfflineFormValidator.Result result = persistRecord();
        if (result != null && !result.isValid()) {
            Toast.makeText(this, getString(R.string.offline_toast_saved_incomplete, result.getInvalidCount()),
                    Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void setupFilterModeSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.offline_form_filter_spinner_item, new String[]{
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
                if (position == FILTER_UNFILLED) {
                    startRequiredEmptyHighlight();
                }
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
        if (item.getItemId() == android.R.id.home) {
            onBackRequested();
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
        // 提交按钮是唯一"显式校验"入口：校验失败时保留错误信息并停留在表单。
        OfflineFormValidator.Result result = OfflineFormValidator.evaluate(_definition, true);
        if (!result.isValid()) {
            showValidationErrors(result);
            Toast.makeText(this, R.string.offline_toast_check_form_input, Toast.LENGTH_SHORT).show();
            return;
        }
        persistRecord(true);
        Toast.makeText(this, R.string.offline_toast_record_saved, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void renderCurrentStep() {
        if (!_formLoaded) {
            _adapter.setDisplayItems(new ArrayList<>());
            _stepTabLayout.setVisibility(View.GONE);
            updateStepScrollHints();
            _itemNavigationLayout.setVisibility(View.GONE);
            if (_quickActions != null) {
                _quickActions.setVisibility(View.GONE);
            }
            return;
        }
        if (_quickActions != null) {
            _quickActions.setVisibility(View.VISIBLE);
        }

        renderStepTabs();
        renderSearchKeyword();
        OfflineFormStep step = _definition.getSteps().get(_currentStepIndex);
        _adapter.setProgressContext(_definition, collectAllFormData());
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
                // 仅必填项参与筛选：存在必填且为空即视为“未填”。
                return hasRequiredEmptyField(item.getNode());
            case FILTER_FILLED:
                // 仅必填项参与筛选：存在必填项且必填项均已填即视为“已填”。
                return hasRequiredField(item.getNode()) && !hasRequiredEmptyField(item.getNode());
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

    /**
     * 节点（含自身与所有后代）是否存在“必填且为空”的表单项。
     * 非必填项不参与该判定，因此不会让所在项被判为“未填”。
     */
    private boolean hasRequiredEmptyField(OfflineFormNode node) {
        if (node == null) {
            return false;
        }
        if (OfflineFormNode.TYPE_FIELD.equals(node.getNodeType())
                && node.getField() != null
                && node.getField().isRequired()
                && node.getField().isEmpty()) {
            return true;
        }
        if (node.getChildren() == null) {
            return false;
        }
        for (OfflineFormNode child : node.getChildren()) {
            if (hasRequiredEmptyField(child)) {
                return true;
            }
        }
        return false;
    }

    /** 节点（含自身与所有后代）是否存在必填的表单项。 */
    private boolean hasRequiredField(OfflineFormNode node) {
        if (node == null) {
            return false;
        }
        if (OfflineFormNode.TYPE_FIELD.equals(node.getNodeType())
                && node.getField() != null
                && node.getField().isRequired()) {
            return true;
        }
        if (node.getChildren() == null) {
            return false;
        }
        for (OfflineFormNode child : node.getChildren()) {
            if (hasRequiredField(child)) {
                return true;
            }
        }
        return false;
    }

    /** 当前表单中“必填且为空”的字段 id 集合，供“仅未填”筛选高亮缺填项。 */
    private List<String> collectRequiredEmptyFieldIds() {
        List<String> result = new ArrayList<>();
        if (_definition == null) {
            return result;
        }
        for (BaseFormItem item : OfflineFormDefinitionFlattener.flattenFields(_definition)) {
            if (item != null && item.isRequired() && item.isEmpty()) {
                result.add(item.getId());
            }
        }
        return result;
    }

    /** 进入“仅未填”时开启一次限时高亮：缺填的必填项高亮 1 秒后自动消失。 */
    private void startRequiredEmptyHighlight() {
        startFieldHighlight(collectRequiredEmptyFieldIds());
    }

    /** 高亮指定字段若干（1 秒后自动清除）；用于“仅未填”筛选与“跳转首个未填必填项”。 */
    private void startFieldHighlight(List<String> fieldIds) {
        _highlightFieldIds.clear();
        if (fieldIds != null) {
            for (String fieldId : fieldIds) {
                if (fieldId != null && !fieldId.isEmpty()) {
                    _highlightFieldIds.add(fieldId);
                }
            }
        }
        _highlightUntil = System.currentTimeMillis() + FIELD_HIGHLIGHT_MS;
        if (_adapter != null) {
            _adapter.notifyDataSetChanged();
        }
        _autoSaveHandler.removeCallbacks(_clearHighlightRunnable);
        _autoSaveHandler.postDelayed(_clearHighlightRunnable, FIELD_HIGHLIGHT_MS);
    }

    private void clearFieldHighlight() {
        if (_highlightUntil == 0L && _highlightFieldIds.isEmpty()) {
            return;
        }
        _highlightUntil = 0L;
        _highlightFieldIds.clear();
        if (_adapter != null) {
            _adapter.notifyDataSetChanged();
        }
    }

    /** 取节点子树中第一个“必填且为空”的表单项 id。 */
    private String findFirstRequiredEmptyFieldId(OfflineFormNode node) {
        if (node == null) {
            return null;
        }
        if (OfflineFormNode.TYPE_FIELD.equals(node.getNodeType())
                && node.getField() != null
                && node.getField().isRequired()
                && node.getField().isEmpty()) {
            return node.getField().getId();
        }
        if (node.getChildren() == null) {
            return null;
        }
        for (OfflineFormNode child : node.getChildren()) {
            String fieldId = findFirstRequiredEmptyFieldId(child);
            if (fieldId != null) {
                return fieldId;
            }
        }
        return null;
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
        scrollToRootItem(position, true);
    }

    private void scrollToRootItem(int position, boolean highlight) {
        _recyclerView.scrollToPosition(position);
        _recyclerView.postDelayed(() -> {
            View itemView = getRootItemView(position);
            if (itemView == null) {
                return;
            }
            int scrollY = Math.max(0, _recyclerView.getTop() + itemView.getTop() - dp(this, 8));
            _formScrollView.smoothScrollTo(0, scrollY);
            if (highlight) {
                playHighlight(itemView);
            }
        }, 80);
    }

    private View getRootItemView(int position) {
        RecyclerView.ViewHolder viewHolder = _recyclerView.findViewHolderForAdapterPosition(position);
        return viewHolder == null ? null : viewHolder.itemView;
    }

    private void updateItemNavigationVisibility() {
        int itemCount = _adapter == null ? 0 : _adapter.getItemCount();
        boolean visible = _formLoaded && itemCount > 1;
        _itemNavigationLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
        updateQuickActionsInset(visible);
    }

    /**
     * 底部导航栏可见时收窄拖动下界，避免悬浮按钮被拖到导航栏上；
     * 贴底距离固定预留导航栏高度，避免导航栏显隐时按钮跳位。
     */
    private void updateQuickActionsInset(boolean navigationVisible) {
        if (_quickActions == null) {
            return;
        }
        int navHeight = navigationVisible
                ? Math.max(_itemNavigationLayout.getHeight(), dp(this, 56))
                : 0;
        _quickActions.setBottomInset(navHeight);
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
        _stepTabLayout.post(this::updateStepScrollHints);
    }

    private void updateStepScrollHints() {
        if (!_formLoaded || _definition == null || _definition.getSteps().size() <= 1
                || _stepTabLayout.getVisibility() != View.VISIBLE) {
            _stepScrollLeftHint.setVisibility(View.GONE);
            _stepScrollRightHint.setVisibility(View.GONE);
            return;
        }

        View tabStrip = _stepTabLayout.getChildAt(0);
        int maxScrollX = tabStrip == null ? 0 : Math.max(0, tabStrip.getWidth() - _stepTabLayout.getWidth());
        if (maxScrollX == 0) {
            _stepScrollLeftHint.setVisibility(View.GONE);
            _stepScrollRightHint.setVisibility(View.GONE);
            return;
        }

        int scrollX = _stepTabLayout.getScrollX();
        _stepScrollLeftHint.setVisibility(scrollX > 1 ? View.VISIBLE : View.GONE);
        _stepScrollRightHint.setVisibility(scrollX < maxScrollX - 1 ? View.VISIBLE : View.GONE);
    }

    private Map<String, String> collectAllFormData() {
        Map<String, String> formData = new HashMap<>();
        for (BaseFormItem item : OfflineFormDefinitionFlattener.flattenFields(_definition)) {
            formData.put(item.getId(), item.getValue());
        }
        return formData;
    }

    private void openManualPdf(String fileName) {
        String patternId = _intent.getStringExtra("patternId");
        File manualFile = OfflineFormFileHelper.getManualFile(this, patternId, fileName);
        if ((manualFile == null || !manualFile.exists()) && "manual.pdf".equals(fileName)) {
            manualFile = OfflineFormFileHelper.getManualPdfFile(this, patternId);
        }
        if (!manualFile.exists()) {
            Toast.makeText(this, R.string.offline_toast_manual_missing, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, PDFPreviewActivity.class);
        intent.putExtra(PDFPreviewActivity.EXTRA_KEY_LOCAL_FILE_PATH, manualFile.getAbsolutePath());
        intent.putExtra(PDFPreviewActivity.EXTRA_KEY_FILENAME, manualFile.getName());
        startActivity(intent);
    }

    /**
     * 收集当前数据、依据校验结果推导状态并落盘。
     *
     * <p>所有落盘点（自动保存 / 返回 / 保存按钮）共用：状态不再是"用户动作的留痕"，
     * 而是"校验结果的推导值"——存在碰过但未通过校验的填报单元即为草稿，否则为待上传。</p>
     *
     * @param forceCreate 无数据时也强制创建记录（附件落盘前需要 recordId）
     * @return 本次校验结果；未落盘（记录已导出 / 空表单）时返回 null
     */
    private OfflineFormValidator.Result persistRecord(boolean forceCreate) {
        if (!_formLoaded || _definition == null) {
            return null;
        }
        if (_editingRecord != null && _editingRecord.getStatus() == OfflineFormRecordStatus.EXPORTED) {
            // 已导出记录保持只读，不再改动。
            return null;
        }
        if (!forceCreate && _editingRecord == null && !hasFilledFormData()) {
            return null;
        }

        OfflineFormValidator.Result result = OfflineFormValidator.evaluate(_definition, false);
        writeRecord(result.isValid() ? OfflineFormRecordStatus.SUBMITTED : OfflineFormRecordStatus.DRAFT);
        return result;
    }

    private OfflineFormValidator.Result persistRecord() {
        return persistRecord(false);
    }

    /** 字段变更后的防抖落盘：短时间内连续变更只落盘一次。 */
    private void scheduleAutoSave() {
        _autoSaveHandler.removeCallbacks(_autoSaveRunnable);
        _autoSaveHandler.postDelayed(_autoSaveRunnable, AUTO_SAVE_DELAY_MS);
    }

    /**
     * 记录某字段因“二次确认后禁用”而被锁定。
     * <p>把字段 id 写入记录的 {@code lockedFieldIds} 并安排落盘，重新进入表单后仍保持禁用。</p>
     */
    private void lockField(String fieldId) {
        if (_definition == null || fieldId == null || fieldId.isEmpty()) {
            return;
        }
        if (_editingRecord == null) {
            _editingRecord = OfflineFormRecord.createDraft(
                    _intent.getStringExtra("patternId"),
                    _intent.getStringExtra("schemaVersion"),
                    collectAllFormData());
        }
        if (_editingRecord.getLockedFieldIds() == null) {
            _editingRecord.setLockedFieldIds(new ArrayList<>());
        }
        if (!_editingRecord.getLockedFieldIds().contains(fieldId)) {
            _editingRecord.getLockedFieldIds().add(fieldId);
            scheduleAutoSave();
        }
    }

    private void writeRecord(OfflineFormRecordStatus status) {
        Map<String, String> formData = collectAllFormData();
        if (_editingRecord == null) {
            _editingRecord = OfflineFormRecord.createDraft(
                    _intent.getStringExtra("patternId"),
                    _intent.getStringExtra("schemaVersion"),
                    formData);
        }
        _editingRecord.setValues(formData);
        _editingRecord.setStatus(status);
        _editingRecord.setUpdatedAt(System.currentTimeMillis());
        _lastPersistedSnapshot = OfflineFormFieldTimes.refresh(_editingRecord, _definition, _lastPersistedSnapshot);
        // 离线填报数据按表单定义目录保存，删除表单定义时可直接清理对应文件夹。
        OfflineFormFileHelper.writeRecord(this, _editingRecord);
    }

    /** 提交按钮的校验失败反馈：回到全部视图、跳转首个失败步骤、展开折叠组并滚动到首个错误。 */
    private void showValidationErrors(OfflineFormValidator.Result result) {
        if (_definition == null || _definition.getSteps() == null || _definition.getSteps().isEmpty()) {
            return;
        }
        if (_filterMode != FILTER_ALL || !_searchKeyword.isEmpty()) {
            _searchKeyword = "";
            _filterMode = FILTER_ALL;
            _filterModeBeforeSearch = FILTER_ALL;
            setFilterModeSelection(FILTER_ALL);
        }
        if (result.getFirstInvalidStepIndex() >= 0) {
            _currentStepIndex = Math.min(result.getFirstInvalidStepIndex(), _definition.getSteps().size() - 1);
        }
        renderCurrentStep();
        _adapter.expandAllGroups();
        scrollToFirstError();
    }

    /** 附件落盘前确保记录存在（只创建记录，不推导状态；附件添加完成后由 persistRecord 统一推导）。 */
    private OfflineFormRecord ensureRecordForAttachment() {
        if (_editingRecord == null) {
            _editingRecord = OfflineFormRecord.createDraft(
                    _intent.getStringExtra("patternId"),
                    _intent.getStringExtra("schemaVersion"),
                    collectAllFormData());
            _lastPersistedSnapshot = OfflineFormFieldTimes.collectValues(_definition);
            OfflineFormFileHelper.writeRecord(this, _editingRecord);
        }
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

    private boolean hasFilledFormData() {
        if (_definition == null) {
            return false;
        }
        for (BaseFormItem item : OfflineFormDefinitionFlattener.flattenFields(_definition)) {
            if (item != null && !item.isEmpty()) {
                return true;
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
        persistRecord();
    }

    private void registerSignatureCaptureLauncher() {
        _signatureCaptureLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            File temporaryFile = null;
            Bitmap bitmap = null;
            try {
                if (result.getResultCode() != RESULT_OK || result.getData() == null
                        || _pendingSignatureItem == null || _pendingSignatureCallback == null) {
                    return;
                }
                if (result.getData().getBooleanExtra(SignatureCaptureActivity.EXTRA_UNCHANGED, false)) {
                    return;
                }
                String outputPath = result.getData().getStringExtra(SignatureCaptureActivity.EXTRA_OUTPUT_PATH);
                if (outputPath == null || outputPath.isEmpty()) {
                    return;
                }

                temporaryFile = new File(outputPath);
                bitmap = BitmapFactory.decodeFile(outputPath);
                if (bitmap == null) {
                    throw new IllegalArgumentException(getString(R.string.offline_error_image_read_failed));
                }

                OfflineFormRecord draft = ensureRecordForAttachment();
                SignatureFormItemValue oldSignature = _pendingSignatureItem.getSignature(_pendingSignatureUserName);
                AttachmentFormItemValue signature = OfflineImageFileHelper.saveSignature(
                        this,
                        draft.getPatternId(),
                        bitmap);
                _pendingSignatureCallback.onSignatureCaptured(signature);
                if (oldSignature != null && oldSignature.getFileName() != null
                        && !oldSignature.getFileName().equals(signature.getFileName())) {
                    OfflineImageFileHelper.deleteLocalFile(
                            this,
                            draft.getPatternId(),
                            oldSignature.getFileName());
                }
                persistRecord();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, getString(R.string.offline_toast_image_save_failed, e.getMessage()), Toast.LENGTH_LONG).show();
            } finally {
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
                if (temporaryFile != null && temporaryFile.exists()) {
                    temporaryFile.delete();
                }
                clearPendingSignatureCapture();
            }
        });
    }

    @Override
    public void captureSignature(SignatureFormItem item, String userName, SignatureCallback callback) {
        _pendingSignatureItem = item;
        _pendingSignatureUserName = userName;
        _pendingSignatureCallback = callback;
        String existingPath = "";
        SignatureFormItemValue existingSignature = item == null ? null : item.getSignature(userName);
        if (existingSignature != null) {
            File existingFile = Utils.resolveLocalFile(this, item.getPatternId(), existingSignature.getFileName());
            if (existingFile != null && existingFile.exists()) {
                existingPath = existingFile.getAbsolutePath();
            }
        }
        _signatureCaptureLauncher.launch(SignatureCaptureActivity.createIntent(
                this,
                item == null ? getString(R.string.offline_title_signature) : item.getTitle(),
                userName,
                item == null ? "" : item.getDisclaimer(),
                existingPath));
    }

    @Override
    public void onSignatureChanged(SignatureFormItem item) {
        persistRecord();
    }

    @Override
    public void uploadFile(FileFormItem item, AttachmentCallback callback) {
        _pendingFileItem = item;
        _pendingFileCallback = callback;
        _fileUploadLauncher.launch(OfflineFileHelper.buildMimeTypes(item.getFileItemConfig()));
    }

    @Override
    public void onFilesChanged(FileFormItem item) {
        persistRecord();
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

    private void clearPendingSignatureCapture() {
        _pendingSignatureItem = null;
        _pendingSignatureUserName = null;
        _pendingSignatureCallback = null;
    }

    /** 跳转到全表单第一个“必填且为空”的表单项：必要时切换步骤并展开分组，然后滚动并高亮。 */
    private void jumpToFirstRequiredEmpty() {
        if (!_formLoaded || _definition == null || _definition.getSteps() == null) {
            return;
        }
        int targetStep = -1;
        OfflineFormNode targetRootNode = null;
        for (int s = 0; s < _definition.getSteps().size() && targetRootNode == null; s++) {
            List<OfflineFormDisplayItem> items = OfflineFormDefinitionFlattener.flattenStep(_definition.getSteps().get(s));
            for (OfflineFormDisplayItem item : items) {
                if (item.getDepth() != 0) {
                    continue;
                }
                if (hasRequiredEmptyField(item.getNode())) {
                    targetStep = s;
                    targetRootNode = item.getNode();
                    break;
                }
            }
        }
        if (targetRootNode == null) {
            Toast.makeText(this, R.string.offline_toast_no_required_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        // 复位筛选，保证目标项可见。
        if (_filterMode != FILTER_ALL || !_searchKeyword.isEmpty()) {
            _searchKeyword = "";
            _filterMode = FILTER_ALL;
            _filterModeBeforeSearch = FILTER_ALL;
            setFilterModeSelection(FILTER_ALL);
        }
        _currentStepIndex = Math.max(0, Math.min(targetStep, _definition.getSteps().size() - 1));
        renderCurrentStep();
        _adapter.expandAllGroups();
        int position = _adapter.getRootPositionForNode(targetRootNode);
        if (position >= 0) {
            // 目标字段由本方法统一高亮，这里不再对整条顶级项闪光。
            scrollToRootItem(position, false);
        }
        String fieldId = findFirstRequiredEmptyFieldId(targetRootNode);
        if (fieldId != null) {
            startFieldHighlight(Collections.singletonList(fieldId));
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
