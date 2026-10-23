package com.huozige.lab.container.offlineform;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
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
import com.huozige.lab.container.offlineform.model.OfflineFormPage;
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
import com.huozige.lab.container.utilities.DeviceUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

public class CustomFormActivity extends AppCompatActivity implements ImageCaptureHost, FileUploadHost, SignatureCaptureHost {
    public static final String EXTRA_RECORD_ID = "recordId";
    private static final String STATE_RECORD_ID = "offline_form_record_id";
    private static final String STATE_CURRENT_STEP_INDEX = "offline_form_current_step_index";
    private static final String STATE_CURRENT_PAGE_INDEX = "offline_form_current_page_index";
    private static final String STATE_FILTER_MODE = "offline_form_filter_mode";
    private static final String STATE_FILTER_MODE_BEFORE_SEARCH = "offline_form_filter_mode_before_search";
    private static final String STATE_SEARCH_KEYWORD = "offline_form_search_keyword";
    private static final int MENU_ID_SAVE_RECORD = 1;
    private static final int FILTER_ALL = 0;
    private static final int FILTER_UNFILLED = 1;
    private static final int FILTER_FILLED = 2;
    private static final int FILTER_SEARCH = 3;
    // 分组目录里的页状态。
    private static final int PAGE_STATUS_TODO = 0;
    private static final int PAGE_STATUS_DOING = 1;
    private static final int PAGE_STATUS_DONE = 2;
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
    private LinearLayout _filterSegmentLayout;
    private LinearLayout _searchInputLayout;
    private LinearLayout _pageIndicatorLayout;
    private TextView _pageNameTextView;
    private TextView _pageCountTextView;
    private View _pageProgressFill;
    private TextView _groupDirectoryButton;
    private TextView _previousItemButton;
    private TextView _nextItemButton;
    private TextView _bottomHintTextView;
    private View _stepFooterDivider;
    private View _filterFooterDivider;
    private EditText _searchInput;
    private TextView _searchFilterButton;
    private TextView _cancelSearchButton;
    private RadioGroup _segFilter;
    private FormAdapter _adapter;
    private Intent _intent;
    private boolean _formLoaded;
    private boolean _editRecordMissingOrOutdated;
    private boolean _updatingTabs;
    private boolean _updatingFilter;
    // 程序化回填搜索框内容时置位，避免触发自身的实时筛选。
    private boolean _syncingSearchInput;
    // 渲染重入保护：任何监听器触发的嵌套 renderCurrentStep 直接忽略，避免无限递归。
    private boolean _renderingStep;
    // 手指是否正按在屏幕上：只把「用户主动滑动」当作移开焦点，忽略键盘弹出导致的自适应滚动。
    private boolean _userTouchInProgress;
    // 软键盘当前是否可见：用户手动收起键盘时同步清除输入焦点。
    private boolean _imeVisible;
    private int _filterMode = FILTER_ALL;
    private int _filterModeBeforeSearch = FILTER_ALL;
    private String _searchKeyword = "";
    private OfflineFormRecord _editingRecord;
    // 上次落盘的"路径→值"快照，用于字段变更检测（仅进程内使用）。
    private Map<String, String> _lastPersistedSnapshot = new HashMap<>();
    private OfflineFormDefinition _definition;
    private int _currentStepIndex;
    // 逐组分页布局下的当前页下标（仅对当前 step 有意义）。
    private int _currentPageIndex;
    // 当前 step 的页集合；flat 布局下始终为空。
    private List<OfflineFormPage> _pages = new ArrayList<>();
    private ActivityResultLauncher<Intent> _imageCaptureLauncher;
    private ActivityResultLauncher<String> _imageUploadLauncher;
    private ActivityResultLauncher<Intent> _imagePreviewLauncher;
    private ActivityResultLauncher<String[]> _fileUploadLauncher;
    private ActivityResultLauncher<Intent> _signatureCaptureLauncher;
    private ImageFormItem _pendingImageItem;
    private AttachmentCallback _pendingImageCallback;
    // 系统相机：输出到应用缓存目录，返回后在后台线程加水印 / 压缩。
    private ActivityResultLauncher<Intent> _systemCameraLauncher;
    private File _pendingSystemCameraFile;
    private ArrayList<String> _pendingSystemCameraWatermarkLines;
    // 图片水印 / 压缩为 CPU 重活，统一放到单线程后台执行，避免快门后主线程卡顿。
    private final ExecutorService _imageProcessExecutor = Executors.newSingleThreadExecutor();
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
        registerSystemCameraLauncher();
        registerImageUploadLauncher();
        registerImagePreviewLauncher();
        registerFileUploadLauncher();
        registerSignatureCaptureLauncher();
        loadFormDataFromJson();
        setupListeners();
        registerImeVisibilityListener();
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
        _imageProcessExecutor.shutdownNow();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        persistRecord();
        if (_editingRecord != null) {
            outState.putString(STATE_RECORD_ID, _editingRecord.getRecordId());
        }
        outState.putInt(STATE_CURRENT_STEP_INDEX, _currentStepIndex);
        outState.putInt(STATE_CURRENT_PAGE_INDEX, _currentPageIndex);
        outState.putInt(STATE_FILTER_MODE, _filterMode);
        outState.putInt(STATE_FILTER_MODE_BEFORE_SEARCH, _filterModeBeforeSearch);
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
        _currentPageIndex = Math.max(0, savedInstanceState.getInt(STATE_CURRENT_PAGE_INDEX, 0));
        _filterMode = savedInstanceState.getInt(STATE_FILTER_MODE, FILTER_ALL);
        if (_filterMode < FILTER_ALL || _filterMode > FILTER_SEARCH) {
            _filterMode = FILTER_ALL;
        }
        _filterModeBeforeSearch = savedInstanceState.getInt(STATE_FILTER_MODE_BEFORE_SEARCH, FILTER_ALL);
        if (_filterModeBeforeSearch < FILTER_ALL || _filterModeBeforeSearch > FILTER_FILLED) {
            _filterModeBeforeSearch = FILTER_ALL;
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
        _filterSegmentLayout = findViewById(R.id.layout_filter_segment);
        _searchInputLayout = findViewById(R.id.layout_search_input);
        _pageIndicatorLayout = findViewById(R.id.layout_page_indicator);
        _pageNameTextView = findViewById(R.id.text_page_name);
        _pageCountTextView = findViewById(R.id.text_page_count);
        _pageProgressFill = findViewById(R.id.view_page_progress_fill);
        _groupDirectoryButton = findViewById(R.id.button_group_directory);
        _previousItemButton = findViewById(R.id.button_previous_item);
        _nextItemButton = findViewById(R.id.button_next_item);
        _bottomHintTextView = findViewById(R.id.text_form_bottom_hint);
        _stepFooterDivider = findViewById(R.id.divider_step_bar_footer);
        _filterFooterDivider = findViewById(R.id.divider_filter_footer);
        _searchInput = findViewById(R.id.edit_search_input);
        _searchFilterButton = findViewById(R.id.button_search_filter);
        _cancelSearchButton = findViewById(R.id.button_cancel_search);
        _segFilter = findViewById(R.id.seg_filter);
        setupBottomHintHeight();
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
        // 滑动表单时立即退出输入状态：避免输入框在滚动后仍保留光标 / 键盘。
        // 仅在手指正在屏幕上（真实滑动）时清理；键盘弹出后系统把输入框顶上来的自适应滚动不清焦点。
        _formScrollView.setOnScrollChangeListener(
                (NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (_userTouchInProgress) {
                        clearInputFocus();
                    }
                });
        _recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState != RecyclerView.SCROLL_STATE_IDLE && _userTouchInProgress) {
                    clearInputFocus();
                }
            }
        });
        _previousItemButton.setOnClickListener(v -> navigateItem(-1));
        _nextItemButton.setOnClickListener(v -> navigateItem(1));
        _searchFilterButton.setOnClickListener(v -> enterSearchMode());
        _cancelSearchButton.setOnClickListener(v -> exitSearchMode(true));
        _searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                onSearchKeywordChanged(s == null ? "" : s.toString());
            }
        });
        _searchInput.setOnEditorActionListener((view, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // 关键词已实时生效，收键盘即可让结果完整可见。
                hideSoftKeyboard();
                _searchInput.clearFocus();
                return true;
            }
            return false;
        });
        _groupDirectoryButton.setOnClickListener(v -> showGroupDirectory());
        _segFilter.setOnCheckedChangeListener((group, checkedId) -> onFilterSegmentChanged(checkedId));
        _stepTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (_updatingTabs || !_formLoaded || tab.getPosition() == _currentStepIndex) {
                    return;
                }
                persistRecord();
                _currentStepIndex = tab.getPosition();
                // 切到新步骤时回到该步骤的第一页；分页与步骤切换互相独立。
                _currentPageIndex = 0;
                resetFilterMode();
                renderCurrentStep();
                resetScroll();
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
     * 搜索态下返回只用来退出搜索，不离开表单。
     */
    private void onBackRequested() {
        if (_filterMode == FILTER_SEARCH) {
            exitSearchMode(true);
            return;
        }
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

    /** 筛选分段（Step 级）：全部 / 仅未填 / 仅已填。 */
    private void onFilterSegmentChanged(int checkedId) {
        if (_updatingFilter) {
            return;
        }
        _searchKeyword = "";
        if (checkedId == R.id.filter_unfilled) {
            _filterMode = FILTER_UNFILLED;
        } else if (checkedId == R.id.filter_filled) {
            _filterMode = FILTER_FILLED;
        } else {
            _filterMode = FILTER_ALL;
        }
        _filterModeBeforeSearch = _filterMode;
        renderCurrentStep();
        if (_filterMode == FILTER_UNFILLED) {
            startRequiredEmptyHighlight();
        }
    }

    /** 复位筛选到「全部」，同时退出搜索态（清空关键词、收起键盘）。 */
    private void resetFilterMode() {
        _filterMode = FILTER_ALL;
        _filterModeBeforeSearch = FILTER_ALL;
        _searchKeyword = "";
        hideSoftKeyboard();
        _searchInput.clearFocus();
        setFilterModeSelection(FILTER_ALL);
    }

    /**
     * 点击「搜索」：不弹窗，直接把筛选行替换为输入框，并拉起键盘开始输入。
     * 进入搜索态时关键词为空，因此列表先退回「全部」结果。
     */
    private void enterSearchMode() {
        if (_filterMode != FILTER_SEARCH) {
            _filterModeBeforeSearch = _filterMode;
            _filterMode = FILTER_SEARCH;
            _searchKeyword = "";
        }
        renderCurrentStep();
        // 输入框刚由 GONE 转 VISIBLE，等一帧再取焦点与键盘，避免弹窗失败。
        _searchInput.post(() -> {
            _searchInput.requestFocus();
            showSoftKeyboard();
        });
    }

    /** 输入框内容变化：实时筛选（keep 焦点，只重绘内容与分页指示器）。 */
    private void onSearchKeywordChanged(String keyword) {
        if (_syncingSearchInput || _filterMode != FILTER_SEARCH) {
            return;
        }
        String normalized = keyword == null ? "" : keyword.trim();
        if (normalized.equals(_searchKeyword)) {
            return;
        }
        _searchKeyword = normalized;
        renderCurrentStep();
    }

    /**
     * 退出搜索态：清空关键词、收起键盘，并把整行还原为分段筛选。
     *
     * @param restorePreviousMode true = 回到进入搜索前的分段项；false = 回到「全部」。
     */
    private void exitSearchMode(boolean restorePreviousMode) {
        hideSoftKeyboard();
        _searchInput.clearFocus();
        _searchKeyword = "";
        int previousMode = _filterModeBeforeSearch;
        if (previousMode < FILTER_ALL || previousMode > FILTER_FILLED) {
            previousMode = FILTER_ALL;
        }
        _filterMode = restorePreviousMode ? previousMode : FILTER_ALL;
        _filterModeBeforeSearch = _filterMode;
        setFilterModeSelection(_filterMode);
        renderCurrentStep();
    }

    private void showSoftKeyboard() {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.showSoftInput(_searchInput, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void hideSoftKeyboard() {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.hideSoftInputFromWindow(_searchInput.getWindowToken(), 0);
        }
    }

    /**
     * 监听软键盘显隐：用户手动收起键盘（返回键 / 收起按钮等）时同步移除输入焦点，
     * 避免键盘已关闭但输入框仍保留光标。
     */
    private void registerImeVisibilityListener() {
        View decorView = getWindow().getDecorView();
        ViewCompat.setOnApplyWindowInsetsListener(decorView, (view, insets) -> {
            boolean imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime());
            if (_imeVisible && !imeVisible) {
                // 键盘刚收起：下一帧清理焦点，避免在 insets 分发过程中触发新的布局请求。
                view.post(this::clearInputFocus);
            }
            _imeVisible = imeVisible;
            return insets;
        });
        ViewCompat.requestApplyInsets(decorView);
    }

    /** 在内容区按字段 id 找到对应 itemView（bind 时打上了 offline_field_id_tag 标记）。 */
    private View findFieldItemView(String fieldId) {
        if (fieldId == null || fieldId.isEmpty() || _recyclerView == null) {
            return null;
        }
        return findFieldItemView(_recyclerView, fieldId);
    }

    private View findFieldItemView(View view, String fieldId) {
        Object tag = view.getTag(R.id.offline_field_id_tag);
        if (fieldId.equals(tag)) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                View found = findFieldItemView(group.getChildAt(i), fieldId);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    /** 滚动到指定字段并播放高亮；找不到视图时退化为按 id 高亮（等待下一次绑定生效）。 */
    private void scrollToAndHighlightField(String fieldId) {
        View fieldView = findFieldItemView(fieldId);
        if (fieldView == null) {
            startFieldHighlight(Collections.singletonList(fieldId));
            return;
        }
        Rect rect = new Rect();
        fieldView.getDrawingRect(rect);
        _formScrollView.offsetDescendantRectToMyCoords(fieldView, rect);
        int targetScrollY = Math.max(0, rect.top - dp(this, 12));
        _formScrollView.smoothScrollTo(0, targetScrollY);
        playHighlight(fieldView);
    }

    /**
     * 退出输入状态：清除当前焦点并收起键盘。
     *
     * <p>用于「移开焦点」场景（点击输入框以外区域、滑动表单），避免输入框滚出屏幕后仍保留光标与键盘。</p>
     */
    private void clearInputFocus() {
        View focused = getCurrentFocus();
        if (focused == null) {
            return;
        }
        focused.clearFocus();
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.hideSoftInputFromWindow(focused.getWindowToken(), 0);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            _userTouchInProgress = true;
            View focused = getCurrentFocus();
            // 点击「当前字段整块」（含字段名所在行）以外的区域时，才取消输入状态；
            // 点字段名或字段内边距不会结束输入。
            if (focused instanceof EditText
                    && !isTouchInsideView(resolveInputTouchArea(focused), event)) {
                clearInputFocus();
            }
        }
        try {
            return super.dispatchTouchEvent(event);
        } finally {
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                // 触摸结束（包括点击后键盘弹出的窗口自适应），不再把随后的滚动当成用户滑动。
                _userTouchInProgress = false;
            }
        }
    }

    /**
     * 输入框的焦点判定区域：向上找到包含字段名（tv_title）的表单项根布局，
     * 使「字段名所在行 + 控件 + 错误文案」整块都属于保留焦点的范围。
     */
    private View resolveInputTouchArea(View input) {
        ViewParent parent = input.getParent();
        while (parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) parent;
            if (group.findViewById(R.id.tv_title) != null) {
                return group;
            }
            parent = group.getParent();
        }
        return input;
    }

    /** 触点是否落在某个 View 的可视范围内（屏幕坐标）。 */
    private boolean isTouchInsideView(View view, MotionEvent event) {
        if (!view.isShown()) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        float x = event.getRawX();
        float y = event.getRawY();
        return x >= location[0] && x < location[0] + view.getWidth()
                && y >= location[1] && y < location[1] + view.getHeight();
    }

    private void setFilterModeSelection(int filterMode) {
        int target = filterMode == FILTER_UNFILLED ? R.id.filter_unfilled
                : filterMode == FILTER_FILLED ? R.id.filter_filled : R.id.filter_all;
        if (_segFilter.getCheckedRadioButtonId() != target) {
            _updatingFilter = true;
            _segFilter.check(target);
            _updatingFilter = false;
        }
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
        if (_renderingStep) {
            return;
        }
        _renderingStep = true;
        try {
            renderCurrentStepInternal();
        } finally {
            _renderingStep = false;
        }
    }

    private void renderCurrentStepInternal() {
        if (!_formLoaded) {
            _adapter.setDisplayItems(new ArrayList<>());
            _stepTabLayout.setVisibility(View.GONE);
            _stepFooterDivider.setVisibility(View.GONE);
            updateStepScrollHints();
            _itemNavigationLayout.setVisibility(View.GONE);
            _pageIndicatorLayout.setVisibility(View.GONE);
            if (_quickActions != null) {
                _quickActions.setVisibility(View.GONE);
            }
            return;
        }
        if (_quickActions != null) {
            // 悬浮快捷按钮（回到顶部 / 跳转未填 / 折叠分组）仅在平铺布局下提供。
            _quickActions.setVisibility(isPagedLayout() ? View.GONE : View.VISIBLE);
        }

        renderStepTabs();
        renderFilterControls();
        OfflineFormStep step = _definition.getSteps().get(_currentStepIndex);
        _adapter.setProgressContext(_definition, collectAllFormData());
        preparePages(step);
        renderCurrentPageContent(step);
        if (step.getTitle() != null && !step.getTitle().isEmpty()) {
            setTitle(step.getTitle());
        }
    }

    /** 是否处于逐组分页布局。 */
    private boolean isPagedLayout() {
        return _definition != null
                && OfflineFormDefinition.LAYOUT_MODE_PAGED.equals(_definition.getLayoutMode());
    }

    /** 依据当前布局准备页集合，并把 _currentPageIndex 收敛到合法范围。 */
    private void preparePages(OfflineFormStep step) {
        if (!isPagedLayout()) {
            _pages = new ArrayList<>();
            _currentPageIndex = 0;
            return;
        }
        // 分页下筛选会改变页数：页集合按当前筛选结果重算。
        List<OfflineFormPage> allPages = OfflineFormDefinitionFlattener.splitIntoPages(step);
        _pages = new ArrayList<>();
        for (OfflineFormPage page : allPages) {
            if (pageMatchesFilter(page)) {
                _pages.add(page);
            }
        }
        _currentPageIndex = clampPageIndex(_currentPageIndex);
    }

    /** 分页模式下，某页是否处于当前筛选结果内（页数随筛选重算）。 */
    private boolean pageMatchesFilter(OfflineFormPage page) {
        if (page == null) {
            return false;
        }
        switch (_filterMode) {
            case FILTER_UNFILLED:
                return hasRequiredUnsatisfiedField(page.getRootNode());
            case FILTER_FILLED:
                return hasRequiredField(page.getRootNode()) && !hasRequiredUnsatisfiedField(page.getRootNode());
            case FILTER_SEARCH:
                if (_searchKeyword.isEmpty()) {
                    return true;
                }
                for (OfflineFormDisplayItem item : page.getItems()) {
                    if (matchesSearch(item)) {
                        return true;
                    }
                }
                return false;
            case FILTER_ALL:
            default:
                return true;
        }
    }

    private int clampPageIndex(int index) {
        if (_pages.isEmpty()) {
            return 0;
        }
        return Math.max(0, Math.min(index, _pages.size() - 1));
    }

    /** 只刷新内容区（展示项 + 分页指示器 + 底部导航态），用于页内翻页。 */
    private void renderCurrentPageContent(OfflineFormStep step) {
        boolean paged = isPagedLayout();
        _adapter.setPagedMode(paged);
        List<OfflineFormDisplayItem> items;
        if (paged) {
            items = _pages.isEmpty() ? new ArrayList<>() : _pages.get(_currentPageIndex).getItems();
        } else {
            items = buildCurrentStepDisplayItems(step);
        }
        _adapter.setDisplayItems(items);
        applyContentSurface(paged);
        renderPageIndicator();
        updateItemNavigationVisibility();
    }

    /**
     * 内容区底色与留白随布局切换。
     *
     * <p>分页布局下整页只有一个表单组，内容直接铺在白色页面上（不再套卡片、不再画行分隔线），
     * 因此去掉 RecyclerView 的四周留白、并把滚动区域铺白，避免页脚露出灰色底；
     * 平铺布局仍用灰色页面底衬托分组卡片。</p>
     */
    private void applyContentSurface(boolean paged) {
        if (_formScrollView != null) {
            _formScrollView.setBackgroundColor(getColor(
                    paged ? R.color.white : R.color.offline_form_page_bg));
        }
        if (_recyclerView != null) {
            int inset = dp(this, 4);
            int padding = paged ? 0 : inset;
            _recyclerView.setPadding(padding, padding, padding, padding);
        }
        if (_filterFooterDivider != null) {
            _filterFooterDivider.setVisibility(paged ? View.VISIBLE : View.GONE);
        }
    }

    private void renderPageIndicator() {
        boolean showIndicator = isPagedLayout() && !_pages.isEmpty()
                // 只有分组页有标题；散项页（连续 text / 裸 field 合并）不展示指示器。
                && isGroupPage(_pages.get(_currentPageIndex));
        if (!showIndicator) {
            _pageIndicatorLayout.setVisibility(View.GONE);
            return;
        }
        _pageIndicatorLayout.setVisibility(View.VISIBLE);
        _pageNameTextView.setText(currentPageTitle());
        _pageCountTextView.setText(getString(R.string.offline_page_count, _currentPageIndex + 1, _pages.size()));
        LinearLayout.LayoutParams progressParams = (LinearLayout.LayoutParams) _pageProgressFill.getLayoutParams();
        progressParams.weight = (float) (_currentPageIndex + 1) / _pages.size();
        _pageProgressFill.setLayoutParams(progressParams);
    }

    private String currentPageTitle() {
        if (_pages.isEmpty()) {
            return "";
        }
        return pageTitle(_pages.get(_currentPageIndex));
    }

    private String pageTitle(OfflineFormPage page) {
        OfflineFormNode node = page == null ? null : page.getRootNode();
        if (node == null) {
            return "";
        }
        String title = node.getTitle();
        if (title != null && !title.isEmpty()) {
            return title;
        }
        if (node.getField() != null && node.getField().getTitle() != null) {
            return node.getField().getTitle();
        }
        return "";
    }

    /** 是否为分组页：分组各自成页，散项（连续 text / 裸 field）合并成一页。 */
    private boolean isGroupPage(OfflineFormPage page) {
        OfflineFormNode node = page == null ? null : page.getRootNode();
        return node != null && OfflineFormNode.TYPE_GROUP.equals(node.getNodeType());
    }

    private void resetScroll() {
        _recyclerView.scrollToPosition(0);
        _formScrollView.scrollTo(0, 0);
    }

    /** 底部导航按布局分流：平铺为滚动跳转，分页为页内翻页。 */
    private void navigateItem(int direction) {
        if (isPagedLayout()) {
            navigatePage(direction);
        } else {
            navigateRootItem(direction);
        }
    }

    private void navigatePage(int direction) {
        if (!isPagedLayout() || _pages.isEmpty()) {
            return;
        }
        int target = _currentPageIndex + direction;
        if (target < 0 || target >= _pages.size()) {
            return;
        }
        _currentPageIndex = target;
        // 刷新进度节点取值的快照，避免翻页后进度文本停留在上一页的数据。
        _adapter.setProgressContext(_definition, collectAllFormData());
        renderCurrentPageContent(_definition.getSteps().get(_currentStepIndex));
        resetScroll();
    }

    private int findPageIndexForRoot(OfflineFormNode rootNode) {
        if (rootNode == null) {
            return -1;
        }
        for (int i = 0; i < _pages.size(); i++) {
            OfflineFormPage page = _pages.get(i);
            if (page.getRootNode() == rootNode) {
                return i;
            }
            // 散项页的 rootNode 只是该页首个节点，其余节点需要逐项比对。
            for (OfflineFormDisplayItem item : page.getItems()) {
                if (item.getNode() == rootNode) {
                    return i;
                }
            }
        }
        return -1;
    }

    private int findPageIndexForField(BaseFormItem field) {
        if (field == null) {
            return -1;
        }
        for (int i = 0; i < _pages.size(); i++) {
            for (OfflineFormDisplayItem item : _pages.get(i).getItems()) {
                if (item.getFormItem() == field) {
                    return i;
                }
            }
        }
        return -1;
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
                return hasRequiredUnsatisfiedField(item.getNode());
            case FILTER_FILLED:
                // 仅必填项参与筛选：存在必填项且必填项均已填即视为“已填”。
                return hasRequiredField(item.getNode()) && !hasRequiredUnsatisfiedField(item.getNode());
            case FILTER_SEARCH:
                return matchesSearch(item);
            case FILTER_ALL:
            default:
                return true;
        }
    }

    /**
     * 渲染筛选行：搜索态显示「输入框 + 取消」，非搜索态显示分段筛选（Step 级，两种布局一致）。
     * 输入框、取消按钮与分段筛选轨道同为 38dp 高、10dp 圆角，切换时行高不跳动。
     */
    private void renderFilterControls() {
        boolean searching = _filterMode == FILTER_SEARCH;
        _filterSegmentLayout.setVisibility(searching ? View.GONE : View.VISIBLE);
        _searchInputLayout.setVisibility(searching ? View.VISIBLE : View.GONE);
        _cancelSearchButton.setVisibility(searching ? View.VISIBLE : View.GONE);
        syncSearchInputText();
        if (!searching) {
            setFilterModeSelection(_filterMode);
        }
    }

    /** 把状态里的关键词回填到输入框（带重入保护，避免输入过程中重置光标）。 */
    private void syncSearchInputText() {
        String current = _searchInput.getText() == null ? "" : _searchInput.getText().toString();
        if (current.equals(_searchKeyword)) {
            return;
        }
        _syncingSearchInput = true;
        _searchInput.setText(_searchKeyword);
        _searchInput.setSelection(_searchKeyword.length());
        _syncingSearchInput = false;
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
     * 节点（含自身与所有后代）是否存在“必填未满足”的表单项。
     * 判定口径为 {@link com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem#isRequiredSatisfied()}：
     * 普通字段为“为空”，开关为“未打开”。非必填项不参与该判定。
     */
    private boolean hasRequiredUnsatisfiedField(OfflineFormNode node) {
        if (node == null) {
            return false;
        }
        if (OfflineFormNode.TYPE_FIELD.equals(node.getNodeType())
                && node.getField() != null
                && node.getField().isRequired()
                && !node.getField().isRequiredSatisfied()) {
            return true;
        }
        if (node.getChildren() == null) {
            return false;
        }
        for (OfflineFormNode child : node.getChildren()) {
            if (hasRequiredUnsatisfiedField(child)) {
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

    /** 取节点子树中第一个“必填未满足”的表单项 id。 */
    private String findFirstRequiredUnsatisfiedFieldId(OfflineFormNode node) {
        if (node == null) {
            return null;
        }
        if (OfflineFormNode.TYPE_FIELD.equals(node.getNodeType())
                && node.getField() != null
                && node.getField().isRequired()
                && !node.getField().isRequiredSatisfied()) {
            return node.getField().getId();
        }
        if (node.getChildren() == null) {
            return null;
        }
        for (OfflineFormNode child : node.getChildren()) {
            String fieldId = findFirstRequiredUnsatisfiedFieldId(child);
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
        if (_itemNavigationLayout == null) {
            return;
        }
        boolean visible;
        if (!_formLoaded) {
            visible = false;
        } else if (isPagedLayout()) {
            // 分页布局下当页可能只有一个顶级项，不能再用 adapter 项数判断；按页数决定。
            visible = _pages.size() > 1;
        } else {
            visible = _adapter != null && _adapter.getItemCount() > 1;
        }
        _itemNavigationLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
        updateItemNavigationButtons();
        // 分页布局下悬浮按钮已隐藏，无需为其预留导航栏高度。
        updateQuickActionsInset(visible && !isPagedLayout());
    }

    /** 分页布局下按页码边界禁用「上一项 / 下一项」；平铺布局下保持始终可用。 */
    private void updateItemNavigationButtons() {
        boolean paged = isPagedLayout();
        boolean previousEnabled = !paged || _currentPageIndex > 0;
        boolean nextEnabled = !paged || _currentPageIndex < _pages.size() - 1;
        _previousItemButton.setEnabled(previousEnabled);
        _nextItemButton.setEnabled(nextEnabled);
        _previousItemButton.setAlpha(previousEnabled ? 1f : 0.4f);
        _nextItemButton.setAlpha(nextEnabled ? 1f : 0.4f);
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
        boolean showTabs = _definition.getSteps().size() > 1;
        _stepTabLayout.setVisibility(showTabs ? View.VISIBLE : View.GONE);
        // 分页布局下 Tab 与页头同处白色区域，用一条发丝线分隔；平铺布局保持原样。
        if (_stepFooterDivider != null) {
            _stepFooterDivider.setVisibility(
                    showTabs && isPagedLayout() ? View.VISIBLE : View.GONE);
        }
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
            resetFilterMode();
        }
        if (result.getFirstInvalidStepIndex() >= 0) {
            _currentStepIndex = Math.min(result.getFirstInvalidStepIndex(), _definition.getSteps().size() - 1);
        }
        renderCurrentStep();
        if (isPagedLayout() && !result.getInvalidFields().isEmpty()) {
            // 分页下定位到首个失败字段所在页；validator 只给到 step 粒度，这里反查所属页。
            int pageIndex = findPageIndexForField(result.getInvalidFields().get(0));
            if (pageIndex >= 0 && pageIndex != _currentPageIndex) {
                _currentPageIndex = pageIndex;
                renderCurrentPageContent(_definition.getSteps().get(_currentStepIndex));
            }
        }
        _adapter.expandAllGroups();
        String firstInvalidFieldId = result.getInvalidFields().isEmpty()
                ? null : result.getInvalidFields().get(0).getId();
        if (firstInvalidFieldId != null) {
            final String fieldId = firstInvalidFieldId;
            // 等展开 / 翻页后的布局完成，再滚到具体失败字段并高亮。
            _formScrollView.postDelayed(() -> scrollToAndHighlightField(fieldId), 80);
        } else {
            scrollToFirstError();
        }
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
            if (item != null && item.hasUserInput()) {
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
        if (item == null || !launchSystemCamera(item)) {
            launchBuiltInCamera(item);
        }
    }

    /** 优先调用系统相机：拍摄、预览与快门全部交给厂商相机 App 处理，打开与出片速度最快。 */
    private boolean launchSystemCamera(ImageFormItem item) {
        if (item == null) {
            return false;
        }
        Intent probeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (probeIntent.resolveActivity(getPackageManager()) == null) {
            return false;
        }
        File outputFile = createSystemCameraOutputFile();
        if (outputFile == null) {
            return false;
        }
        Uri outputUri = DeviceUtility.pathToUri(outputFile.getAbsolutePath());
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        _pendingSystemCameraFile = outputFile;
        _pendingSystemCameraWatermarkLines = OfflineImageFileHelper.buildWatermarkLines(item);
        try {
            _systemCameraLauncher.launch(cameraIntent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            clearSystemCameraPending();
            return false;
        }
    }

    /** 设备没有可用的系统相机 App 时的回退：沿用应用内自绘相机页（带水印与确认页）。 */
    private void launchBuiltInCamera(ImageFormItem item) {
        if (item == null) {
            clearPendingImageCapture();
            return;
        }
        Intent cameraIntent = new Intent(this, CameraViewActivity.class);
        cameraIntent.putExtra(CameraViewActivity.EXTRA_OPERATION, CameraViewActivity.OPERATION_TAKE_PHOTO);
        cameraIntent.putStringArrayListExtra(CameraViewActivity.EXTRA_WATERMARK_LINES, OfflineImageFileHelper.buildWatermarkLines(item));
        cameraIntent.putExtra(CameraViewActivity.EXTRA_CONFIRM_PHOTO, true);
        _imageCaptureLauncher.launch(cameraIntent);
    }

    private void registerSystemCameraLauncher() {
        _systemCameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            File outputFile = _pendingSystemCameraFile;
            boolean success = result.getResultCode() == RESULT_OK
                    && outputFile != null && outputFile.exists() && outputFile.length() > 0;
            if (!success) {
                if (result.getResultCode() == RESULT_OK && outputFile != null) {
                    // 相机返回成功但输出文件无效：明确提示，避免用户误以为照片已添加。
                    Toast.makeText(this, R.string.offline_toast_image_file_missing, Toast.LENGTH_SHORT).show();
                }
                if (_pendingImageCallback != null) {
                    _pendingImageCallback.onAttachmentsProcessingCancelled();
                }
                clearSystemCameraPending();
                clearPendingImageCapture();
                return;
            }
            startSystemCameraProcessing(outputFile);
        });
    }

    /**
     * 系统相机返回后：先在网格显示「处理中」占位，再在后台线程完成 EXIF 旋转 / 水印 / 压缩，
     * 最后回主线程把图片加入表单并落盘。
     */
    private void startSystemCameraProcessing(File outputFile) {
        final ImageFormItem item = _pendingImageItem;
        final AttachmentCallback callback = _pendingImageCallback;
        final ArrayList<String> watermarkLines = _pendingSystemCameraWatermarkLines;
        if (item == null || callback == null) {
            clearSystemCameraPending();
            clearPendingImageCapture();
            return;
        }
        final OfflineFormRecord draft;
        try {
            draft = ensureRecordForAttachment();
        } catch (Exception e) {
            e.printStackTrace();
            callback.onAttachmentsProcessingCancelled();
            Toast.makeText(this, getString(R.string.offline_toast_image_save_failed, e.getMessage()), Toast.LENGTH_LONG).show();
            clearSystemCameraPending();
            clearPendingImageCapture();
            return;
        }
        final String patternId = draft.getPatternId();
        final Context appContext = getApplicationContext();
        final Uri sourceUri = Uri.fromFile(outputFile);
        callback.onAttachmentsProcessingStarted();
        _imageProcessExecutor.execute(() -> {
            try {
                AttachmentFormItemValue image = OfflineImageFileHelper.saveCameraImage(
                        appContext, patternId, item, sourceUri, watermarkLines);
                runOnUiThread(() -> {
                    if (isFinishing() || isDestroyed()) {
                        return;
                    }
                    item.setProcessing(false);
                    callback.onAttachmentsSelected(Collections.singletonList(image));
                    persistRecord();
                    clearSystemCameraPending();
                    clearPendingImageCapture();
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    if (isFinishing() || isDestroyed()) {
                        return;
                    }
                    item.setProcessing(false);
                    callback.onAttachmentsProcessingCancelled();
                    Toast.makeText(this, getString(R.string.offline_toast_image_save_failed, e.getMessage()), Toast.LENGTH_LONG).show();
                    clearSystemCameraPending();
                    clearPendingImageCapture();
                });
            }
        });
    }

    private File createSystemCameraOutputFile() {
        File dir = new File(getCacheDir(), "camera");
        if (!dir.exists() && !dir.mkdirs()) {
            return null;
        }
        return new File(dir, "capture_" + System.currentTimeMillis() + ".jpg");
    }

    private void clearSystemCameraPending() {
        File file = _pendingSystemCameraFile;
        _pendingSystemCameraFile = null;
        _pendingSystemCameraWatermarkLines = null;
        if (file != null && file.exists()) {
            file.delete();
        }
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
                if (hasRequiredUnsatisfiedField(item.getNode())) {
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
            resetFilterMode();
        }
        _currentStepIndex = Math.max(0, Math.min(targetStep, _definition.getSteps().size() - 1));
        renderCurrentStep();
        if (isPagedLayout()) {
            // 分页下目标顶级项即一页，直接翻到该页后整页可见，无需滚动定位。
            int pageIndex = findPageIndexForRoot(targetRootNode);
            if (pageIndex >= 0 && pageIndex != _currentPageIndex) {
                _currentPageIndex = pageIndex;
                renderCurrentPageContent(_definition.getSteps().get(_currentStepIndex));
            }
        }
        _adapter.expandAllGroups();
        if (isPagedLayout()) {
            resetScroll();
        } else {
            int position = _adapter.getRootPositionForNode(targetRootNode);
            if (position >= 0) {
                // 目标字段由本方法统一高亮，这里不再对整条顶级项闪光。
                scrollToRootItem(position, false);
            }
        }
        String fieldId = findFirstRequiredUnsatisfiedFieldId(targetRootNode);
        if (fieldId != null) {
            final String targetFieldId = fieldId;
            _formScrollView.postDelayed(() -> scrollToAndHighlightField(targetFieldId), 80);
        }
    }

    private void scrollToFirstError() {
        if (isPagedLayout()) {
            // 每页内容有限，直接回到页首即可看到错误。
            resetScroll();
            return;
        }
        List<BaseFormItem> items = _adapter.getFormItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getErrorMessage() != null) {
                _recyclerView.scrollToPosition(i);
                break;
            }
        }
    }

    /** 打开「分组目录」：列出所有页（分组），可跳转并显示完成状态。分组很多时用它导航。 */
    private void showGroupDirectory() {
        if (!isPagedLayout() || _pages.isEmpty()) {
            return;
        }
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View content = LayoutInflater.from(this).inflate(R.layout.offline_form_group_directory_sheet, null);
        TextView titleView = content.findViewById(R.id.group_directory_title);
        TextView subtitleView = content.findViewById(R.id.group_directory_subtitle);
        LinearLayout list = content.findViewById(R.id.group_directory_list);

        titleView.setText(R.string.offline_dialog_group_directory_title);

        int done = 0;
        int doing = 0;
        for (OfflineFormPage page : _pages) {
            int status = pageStatus(page);
            if (status == PAGE_STATUS_DONE) {
                done++;
            } else if (status == PAGE_STATUS_DOING) {
                doing++;
            }
        }
        subtitleView.setText(getString(R.string.offline_group_directory_summary, _pages.size(), done, doing));

        for (int i = 0; i < _pages.size(); i++) {
            list.addView(createDirectoryRow(dialog, i));
        }

        dialog.setContentView(content);
        dialog.show();
    }

    private View createDirectoryRow(BottomSheetDialog dialog, int pageIndex) {
        OfflineFormPage page = _pages.get(pageIndex);
        boolean current = pageIndex == _currentPageIndex;
        // 分组目录与顶部页头同一套字阶：序号 13sp、组名 15sp、状态徽标 12sp。
        int metaTextSize = getResources().getDimensionPixelSize(R.dimen.offline_form_chrome_meta_text_size);
        int chromeTextSize = getResources().getDimensionPixelSize(R.dimen.offline_form_chrome_text_size);

        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(dp(this, 10), dp(this, 11), dp(this, 10), dp(this, 11));
        if (current) {
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(getColor(R.color.offline_form_brand_tint));
            bg.setCornerRadius(dp(this, 10));
            row.setBackground(bg);
        }

        TextView numberView = new TextView(this);
        numberView.setText(String.format(Locale.CHINA, "%02d", pageIndex + 1));
        numberView.setTextSize(TypedValue.COMPLEX_UNIT_PX, metaTextSize);
        numberView.setTypeface(null, android.graphics.Typeface.BOLD);
        numberView.setTextColor(getColor(current ? R.color.huozige_blue : R.color.offline_form_muted));
        numberView.setGravity(Gravity.END);
        row.addView(numberView, new LinearLayout.LayoutParams(dp(this, 26), ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView nameView = new TextView(this);
        nameView.setText(directoryPageName(page));
        nameView.setTextSize(TypedValue.COMPLEX_UNIT_PX, chromeTextSize);
        nameView.setTypeface(null, android.graphics.Typeface.BOLD);
        nameView.setTextColor(getColor(R.color.offline_form_ink));
        nameView.setSingleLine(true);
        nameView.setEllipsize(android.text.TextUtils.TruncateAt.END);
        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        nameParams.leftMargin = dp(this, 12);
        nameParams.rightMargin = dp(this, 8);
        row.addView(nameView, nameParams);

        int status = pageStatus(page);
        TextView statusView = new TextView(this);
        statusView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        statusView.setTypeface(null, android.graphics.Typeface.BOLD);
        statusView.setPadding(dp(this, 8), dp(this, 3), dp(this, 8), dp(this, 3));
        GradientDrawable statusBg = new GradientDrawable();
        statusBg.setCornerRadius(dp(this, 20));
        if (status == PAGE_STATUS_DONE) {
            statusView.setText(R.string.offline_group_status_done);
            statusView.setTextColor(getColor(R.color.offline_form_ok));
            statusBg.setColor(0xFFE7F7EE);
        } else if (status == PAGE_STATUS_DOING) {
            statusView.setText(R.string.offline_group_status_doing);
            statusView.setTextColor(getColor(R.color.offline_form_brand_deep));
            statusBg.setColor(getColor(R.color.offline_form_brand_tint));
        } else {
            statusView.setText(R.string.offline_group_status_todo);
            statusView.setTextColor(getColor(R.color.offline_form_muted));
            statusBg.setColor(0xFFEEF1F4);
        }
        statusView.setBackground(statusBg);
        row.addView(statusView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        row.setOnClickListener(v -> {
            dialog.dismiss();
            if (pageIndex != _currentPageIndex) {
                _currentPageIndex = pageIndex;
                renderCurrentStep();
                resetScroll();
            }
        });
        return row;
    }

    private String directoryPageName(OfflineFormPage page) {
        String title = pageTitle(page);
        return title == null || title.isEmpty()
                ? getString(R.string.offline_group_directory_untitled)
                : title;
    }

    private int pageStatus(OfflineFormPage page) {
        OfflineFormNode root = page.getRootNode();
        if (hasRequiredField(root) && !hasRequiredUnsatisfiedField(root)) {
            return PAGE_STATUS_DONE;
        }
        if (pageHasUserInput(page)) {
            return PAGE_STATUS_DOING;
        }
        return PAGE_STATUS_TODO;
    }

    private boolean pageHasUserInput(OfflineFormPage page) {
        for (OfflineFormDisplayItem item : page.getItems()) {
            if (item.isField() && item.getFormItem() != null && item.getFormItem().hasUserInput()) {
                return true;
            }
        }
        return false;
    }

}
