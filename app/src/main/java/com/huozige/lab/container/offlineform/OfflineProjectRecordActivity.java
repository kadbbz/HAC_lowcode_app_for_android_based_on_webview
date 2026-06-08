package com.huozige.lab.container.offlineform;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Gravity;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.huozige.lab.container.BaseActivity;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.formitem.OfflineFormItemRegistry;
import com.huozige.lab.container.offlineform.model.OfflineComputedInfo;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFlattener;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;
import com.huozige.lab.container.offlineform.model.OfflineFormRecordStatus;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

public class OfflineProjectRecordActivity extends BaseActivity {
    private static final int MENU_ID_CONFIG_MANAGE = 1;
    private static final int MENU_ID_RECORD_EDIT = 1;
    private static final int MENU_ID_RECORD_DELETE = 2;
    private static final int MENU_ID_DRAFT_CONTINUE = 1;
    private static final int MENU_ID_DRAFT_DELETE = 2;
    private static final int[] RECORD_PAGE_SIZE_OPTIONS = new int[]{10, 20, 50, 100};

    private String _patternId;
    private String _title;
    private String _description;
    private String _schemaVersion;
    private LinearLayout _draftListLayout;
    private LinearLayout _recordListLayout;
    private TextView _emptyRecordTextView;
    private int _recordPageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_form_project_record_activity);

        _patternId = getIntent().getStringExtra("patternId");
        _title = getIntent().getStringExtra("title");
        _description = getIntent().getStringExtra("description");
        _schemaVersion = getIntent().getStringExtra("schemaVersion");

        setTitle(_title == null || _title.isEmpty() ? getString(R.string.offline_title_project_record) : _title);
        TextView projectDescriptionTextView = findViewById(R.id.projectDescriptionTextView);
        projectDescriptionTextView.setText(_description == null ? "" : _description);

        _draftListLayout = findViewById(R.id.draftListLayout);
        _recordListLayout = findViewById(R.id.recordListLayout);
        _emptyRecordTextView = findViewById(R.id.emptyRecordTextView);

        findViewById(R.id.cmdNewRecord).setOnClickListener(v -> openNewRecord());
        renderRecords();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hasPatternId() && OfflineFormFileHelper.readDefinition(this, _patternId) == null) {
            finish();
            return;
        }
        renderRecords();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_ID_CONFIG_MANAGE, MENU_ID_CONFIG_MANAGE, R.string.offline_menu_config_manage);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MENU_ID_CONFIG_MANAGE) {
            showConfigDetail();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openNewRecord() {
        if (!hasPatternId()) {
            Toast.makeText(this, R.string.offline_toast_config_missing, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, CustomFormActivity.class);
        intent.putExtra("patternId", _patternId);
        intent.putExtra("title", _title);
        intent.putExtra("description", _description);
        intent.putExtra("schemaVersion", _schemaVersion);
        startActivity(intent);
    }

    private void renderRecords() {
        if (_recordListLayout == null) {
            return;
        }

        _recordListLayout.removeAllViews();
        if (!hasPatternId()) {
            _emptyRecordTextView.setVisibility(View.VISIBLE);
            return;
        }

        OfflineFormDefinitionFile definitionFile = OfflineFormFileHelper.readDefinition(this, _patternId);
        if (definitionFile == null || definitionFile.getJsonSchema() == null) {
            _emptyRecordTextView.setVisibility(View.VISIBLE);
            return;
        }

        List<OfflineFormRecord> draftRecords = new ArrayList<>();
        List<OfflineFormRecord> historyRecords = new ArrayList<>();
        for (OfflineFormRecord record : OfflineFormFileHelper.readRecords(this, _patternId)) {
            if (record.getStatus() == OfflineFormRecordStatus.DRAFT) {
                draftRecords.add(record);
            } else {
                historyRecords.add(record);
            }
        }

        renderDraftRecords(draftRecords, definitionFile.getJsonSchema().getSchemaVersion());
        _emptyRecordTextView.setVisibility(historyRecords.isEmpty() ? View.VISIBLE : View.GONE);
        if (!historyRecords.isEmpty()) {
            _recordListLayout.addView(createRecordTable(historyRecords, definitionFile));
        }
    }

    private void renderDraftRecords(List<OfflineFormRecord> draftRecords, String currentSchemaVersion) {
        _draftListLayout.removeAllViews();
        for (OfflineFormRecord draftRecord : draftRecords) {
            _draftListLayout.addView(createDraftRecordCard(draftRecord, currentSchemaVersion));
        }
    }

    private View createDraftRecordCard(OfflineFormRecord record, String currentSchemaVersion) {
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(this, 96));
        cardParams.setMargins(0, dp(this, 8), 0, 0);
        cardView.setLayoutParams(cardParams);
        cardView.setCardBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        cardView.setRadius(dp(this, 8));
        cardView.setCardElevation(dp(this, 3));
        cardView.setUseCompatPadding(true);
        cardView.setContentPadding(dp(this, 18), dp(this, 18), dp(this, 10), dp(this, 18));
        cardView.setClickable(true);
        cardView.setFocusable(true);
        cardView.setForeground(ContextCompat.getDrawable(this, android.R.drawable.list_selector_background));
        cardView.setOnClickListener(v -> openRecord(record, currentSchemaVersion));

        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);

        ImageView iconView = new ImageView(this);
        iconView.setImageResource(R.drawable.ic_offline_draft_record);
        iconView.setContentDescription(getString(R.string.offline_title_draft_record));
        row.addView(iconView, new LinearLayout.LayoutParams(dp(this, 40), dp(this, 40)));

        LinearLayout textLayout = new LinearLayout(this);
        textLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        textParams.setMargins(dp(this, 14), 0, 0, 0);
        row.addView(textLayout, textParams);

        TextView titleView = new TextView(this);
        titleView.setText(R.string.offline_title_draft_record);
        titleView.setTextColor(ContextCompat.getColor(this, android.R.color.black));
        titleView.setTextSize(18);
        titleView.setTypeface(null, Typeface.BOLD);
        textLayout.addView(titleView);

        TextView summaryView = new TextView(this);
        summaryView.setText(getString(R.string.offline_summary_draft_record, formatRecordTime(record.getUpdatedAt())));
        summaryView.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray));
        summaryView.setTextSize(14);
        summaryView.setSingleLine(true);
        summaryView.setEllipsize(TextUtils.TruncateAt.END);
        LinearLayout.LayoutParams summaryParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        summaryParams.setMargins(0, dp(this, 6), 0, 0);
        textLayout.addView(summaryView, summaryParams);

        TextView moreButton = createActionButton(getString(R.string.offline_button_more), android.R.color.black);
        moreButton.setOnClickListener(v -> {
            v.setPressed(false);
            showDraftActionMenu(moreButton, record, currentSchemaVersion);
        });
        row.addView(moreButton, new LinearLayout.LayoutParams(dp(this, 40), LinearLayout.LayoutParams.MATCH_PARENT));

        cardView.addView(row);
        return cardView;
    }

    private View createRecordTable(List<OfflineFormRecord> records, OfflineFormDefinitionFile definitionFile) {
        int pageSize = getRecordPageSize(definitionFile);
        int pageCount = (records.size() + pageSize - 1) / pageSize;
        if (_recordPageIndex >= pageCount) {
            _recordPageIndex = Math.max(0, pageCount - 1);
        }
        int fromIndex = _recordPageIndex * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, records.size());
        List<OfflineFormRecord> currentPageRecords = records.subList(fromIndex, toIndex);

        LinearLayout wrapper = new LinearLayout(this);
        wrapper.setOrientation(LinearLayout.VERTICAL);
        wrapper.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        cardParams.setMargins(0, dp(this, 4), 0, dp(this, 8));
        cardView.setLayoutParams(cardParams);
        cardView.setCardBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        cardView.setRadius(dp(this, 8));
        cardView.setCardElevation(dp(this, 2));
        cardView.setUseCompatPadding(true);
        cardView.setContentPadding(dp(this, 8), dp(this, 8), dp(this, 8), dp(this, 8));

        LinearLayout tableContainer = new LinearLayout(this);
        tableContainer.setOrientation(LinearLayout.HORIZONTAL);
        tableContainer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        HorizontalScrollView scrollView = new HorizontalScrollView(this);
        scrollView.setHorizontalScrollBarEnabled(true);
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1));

        TableLayout dataTableLayout = new TableLayout(this);
        dataTableLayout.setShrinkAllColumns(false);
        dataTableLayout.setStretchAllColumns(false);

        TableLayout actionTableLayout = new TableLayout(this);
        actionTableLayout.setLayoutParams(new LinearLayout.LayoutParams(
                dp(this, 44),
                LinearLayout.LayoutParams.WRAP_CONTENT));
        actionTableLayout.setShrinkAllColumns(false);
        actionTableLayout.setStretchAllColumns(false);

        List<String> displayColumns = getDisplayColumns(definitionFile);
        Map<String, String> columnTitles = buildColumnTitles(definitionFile);
        Map<String, BaseFormItem> formItems = buildFormItems(definitionFile);
        String currentSchemaVersion = definitionFile.getJsonSchema().getSchemaVersion();

        dataTableLayout.addView(createHeaderRow(displayColumns, columnTitles));
        actionTableLayout.addView(createActionHeaderRow());
        for (OfflineFormRecord record : currentPageRecords) {
            dataTableLayout.addView(createRecordRow(record, displayColumns, formItems, currentSchemaVersion));
            actionTableLayout.addView(createActionRow(record, currentSchemaVersion));
        }

        scrollView.addView(dataTableLayout);
        tableContainer.addView(scrollView);
        tableContainer.addView(actionTableLayout);
        cardView.addView(tableContainer);
        wrapper.addView(cardView);
        if (records.size() > pageSize) {
            wrapper.addView(createPaginationBar(pageCount));
        }
        return wrapper;
    }

    private View createPaginationBar(int pageCount) {
        LinearLayout paginationLayout = new LinearLayout(this);
        paginationLayout.setOrientation(LinearLayout.HORIZONTAL);
        paginationLayout.setGravity(Gravity.CENTER);
        paginationLayout.setPadding(0, dp(this, 8), 0, dp(this, 4));

        TextView previousButton = createPaginationButton(getString(R.string.offline_button_previous_page), _recordPageIndex > 0);
        previousButton.setOnClickListener(v -> {
            if (_recordPageIndex <= 0) {
                return;
            }
            _recordPageIndex--;
            renderRecords();
        });

        TextView pageInfoTextView = new TextView(this);
        pageInfoTextView.setText(getString(R.string.offline_text_record_page_info, _recordPageIndex + 1, pageCount));
        pageInfoTextView.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray));
        pageInfoTextView.setTextSize(14);
        pageInfoTextView.setGravity(Gravity.CENTER);
        pageInfoTextView.setPadding(dp(this, 14), 0, dp(this, 14), 0);

        TextView nextButton = createPaginationButton(getString(R.string.offline_button_next_page), _recordPageIndex < pageCount - 1);
        nextButton.setOnClickListener(v -> {
            if (_recordPageIndex >= pageCount - 1) {
                return;
            }
            _recordPageIndex++;
            renderRecords();
        });

        paginationLayout.addView(previousButton);
        paginationLayout.addView(pageInfoTextView);
        paginationLayout.addView(nextButton);
        return paginationLayout;
    }

    private TextView createPaginationButton(String text, boolean enabled) {
        TextView button = new TextView(this);
        button.setText(text);
        button.setTextSize(14);
        button.setGravity(Gravity.CENTER);
        button.setMinWidth(dp(this, 72));
        button.setPadding(dp(this, 10), dp(this, 8), dp(this, 10), dp(this, 8));
        button.setEnabled(enabled);
        button.setClickable(enabled);
        button.setTextColor(ContextCompat.getColor(this, enabled ? R.color.huozige_blue : android.R.color.darker_gray));
        if (enabled) {
            button.setBackgroundResource(android.R.drawable.list_selector_background);
        }
        return button;
    }

    private TableRow createHeaderRow(List<String> displayColumns, Map<String, String> columnTitles) {
        TableRow row = new TableRow(this);
        for (String fieldId : displayColumns) {
            row.addView(createCell(resolveColumnTitle(columnTitles, fieldId), true));
        }
        row.addView(createCell(getString(R.string.offline_table_column_version), true));
        row.addView(createCell(getString(R.string.offline_table_column_updated_at), true));
        return row;
    }

    private TableRow createActionHeaderRow() {
        TableRow row = new TableRow(this);
        row.addView(createCell(getString(R.string.offline_table_column_actions), true));
        return row;
    }

    private TableRow createRecordRow(OfflineFormRecord record, List<String> displayColumns, Map<String, BaseFormItem> formItems, String currentSchemaVersion) {
        TableRow row = new TableRow(this);
        row.setClickable(true);
        row.setBackgroundResource(android.R.drawable.list_selector_background);
        for (String fieldId : displayColumns) {
            row.addView(createRecordValueCell(record, fieldId, formItems.get(fieldId)));
        }
        row.addView(createCell(record.getSchemaVersion(), false));
        row.addView(createCell(formatRecordTime(record.getUpdatedAt()), false));
        row.setOnClickListener(v -> openRecord(record, currentSchemaVersion));
        return row;
    }

    private TableRow createActionRow(OfflineFormRecord record, String currentSchemaVersion) {
        TableRow row = new TableRow(this);
        row.addView(createActionCell(record, currentSchemaVersion));
        return row;
    }

    private TextView createCell(String text, boolean header) {
        TextView textView = new TextView(this);
        textView.setMinWidth(dp(this, 120));
        textView.setMaxWidth(dp(this, 220));
        textView.setPadding(dp(this, 12), dp(this, 10), dp(this, 12), dp(this, 10));
        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setText(text == null ? "" : text);
        textView.setTextSize(header ? 14 : 13);
        textView.setTextColor(ContextCompat.getColor(this, header ? android.R.color.black : android.R.color.darker_gray));
        if (header) {
            textView.setTypeface(null, Typeface.BOLD);
        }
        return textView;
    }

    private View createRecordValueCell(OfflineFormRecord record, String fieldId, BaseFormItem formItem) {
        String rawValue = getRecordValue(record, fieldId);
        if (formItem == null) {
            return createCell(rawValue, false);
        }
        return OfflineFormItemRegistry.createReadOnlyView(this, formItem, rawValue, true);
    }

    private View createActionCell(OfflineFormRecord record, String currentSchemaVersion) {
        LinearLayout actions = new LinearLayout(this);
        actions.setOrientation(LinearLayout.HORIZONTAL);
        actions.setPadding(dp(this, 4), dp(this, 2), dp(this, 4), dp(this, 2));
        actions.setMinimumWidth(dp(this, 24));

        TextView moreButton = createActionButton(getString(R.string.offline_button_more), android.R.color.black);
        moreButton.setOnClickListener(v -> showRecordActionMenu(moreButton, record, currentSchemaVersion));
        actions.addView(moreButton);

        return actions;
    }

    private TextView createActionButton(String text, int colorResId) {
        TextView button = new TextView(this);
        button.setText(text);
        button.setTextSize(13);
        button.setTypeface(null, Typeface.BOLD);
        button.setTextColor(ContextCompat.getColor(this, colorResId));
        button.setGravity(android.view.Gravity.CENTER);
        button.setPadding(dp(this, 6), dp(this, 8), dp(this, 6), dp(this, 8));
        button.setMinWidth(dp(this, 36));
        button.setClickable(true);
        button.setBackgroundResource(android.R.drawable.list_selector_background);
        return button;
    }

    private void showRecordActionMenu(View anchor, OfflineFormRecord record, String currentSchemaVersion) {
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        popupMenu.getMenu().add(0, MENU_ID_RECORD_EDIT, MENU_ID_RECORD_EDIT, R.string.offline_button_edit);
        popupMenu.getMenu().add(0, MENU_ID_RECORD_DELETE, MENU_ID_RECORD_DELETE, R.string.offline_button_delete);
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == MENU_ID_RECORD_EDIT) {
                openRecord(record, currentSchemaVersion);
                return true;
            }
            if (item.getItemId() == MENU_ID_RECORD_DELETE) {
                confirmDeleteRecord(record);
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    private void showDraftActionMenu(View anchor, OfflineFormRecord record, String currentSchemaVersion) {
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        popupMenu.getMenu().add(0, MENU_ID_DRAFT_CONTINUE, MENU_ID_DRAFT_CONTINUE, R.string.offline_button_continue_draft);
        popupMenu.getMenu().add(0, MENU_ID_DRAFT_DELETE, MENU_ID_DRAFT_DELETE, R.string.offline_button_delete);
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == MENU_ID_DRAFT_CONTINUE) {
                openRecord(record, currentSchemaVersion);
                return true;
            }
            if (item.getItemId() == MENU_ID_DRAFT_DELETE) {
                confirmDeleteRecord(record);
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    private void openRecord(OfflineFormRecord record, String currentSchemaVersion) {
        if (!currentSchemaVersion.equals(record.getSchemaVersion())) {
            Toast.makeText(this, R.string.offline_toast_old_record_edit_not_supported, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, CustomFormActivity.class);
        intent.putExtra("patternId", _patternId);
        intent.putExtra("title", _title);
        intent.putExtra("description", _description);
        intent.putExtra("schemaVersion", record.getSchemaVersion());
        intent.putExtra(CustomFormActivity.EXTRA_RECORD_ID, record.getRecordId());
        startActivity(intent);
    }

    private void confirmDeleteRecord(OfflineFormRecord record) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.offline_dialog_delete_title)
                .setMessage(R.string.offline_dialog_delete_record_message)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(R.string.offline_button_delete, (dialog, which) -> deleteRecord(record))
                .show();
    }

    private void deleteRecord(OfflineFormRecord record) {
        boolean success = OfflineFormFileHelper.deleteRecord(this, _patternId, record.getRecordId());
        Toast.makeText(this, success ? R.string.offline_toast_delete_success : R.string.offline_toast_delete_failed, Toast.LENGTH_SHORT).show();
        if (success) {
            renderRecords();
        }
    }

    private List<String> getDisplayColumns(OfflineFormDefinitionFile definitionFile) {
        if (definitionFile.getComputed() != null && definitionFile.getComputed().getDisplayColumns() != null) {
            return definitionFile.getComputed().getDisplayColumns();
        }
        return new ArrayList<>();
    }

    private int getRecordPageSize(OfflineFormDefinitionFile definitionFile) {
        if (definitionFile.getComputed() == null || definitionFile.getComputed().getRecordPageSize() <= 0) {
            return OfflineComputedInfo.DEFAULT_RECORD_PAGE_SIZE;
        }
        int pageSize = definitionFile.getComputed().getRecordPageSize();
        for (int option : RECORD_PAGE_SIZE_OPTIONS) {
            if (option == pageSize) {
                return pageSize;
            }
        }
        return OfflineComputedInfo.DEFAULT_RECORD_PAGE_SIZE;
    }

    private Map<String, String> buildColumnTitles(OfflineFormDefinitionFile definitionFile) {
        Map<String, String> titles = new HashMap<>();
        if (definitionFile.getJsonSchema() == null) {
            return titles;
        }
        for (BaseFormItem formItem : OfflineFormDefinitionFlattener.flattenFields(definitionFile.getJsonSchema())) {
            titles.put(formItem.getId(), formItem.getTitle());
        }
        return titles;
    }

    private Map<String, BaseFormItem> buildFormItems(OfflineFormDefinitionFile definitionFile) {
        Map<String, BaseFormItem> formItems = new HashMap<>();
        if (definitionFile.getJsonSchema() == null) {
            return formItems;
        }
        for (BaseFormItem formItem : OfflineFormDefinitionFlattener.flattenFields(definitionFile.getJsonSchema())) {
            formItems.put(formItem.getId(), formItem);
        }
        return formItems;
    }

    private String resolveColumnTitle(Map<String, String> columnTitles, String fieldId) {
        String title = columnTitles.get(fieldId);
        return title == null || title.isEmpty() ? fieldId : title;
    }

    private String getRecordValue(OfflineFormRecord record, String fieldId) {
        if (record.getValues() == null) {
            return "";
        }
        String value = record.getValues().get(fieldId);
        return value == null ? "" : value;
    }

    private void showConfigDetail() {
        if (!hasPatternId()) {
            Toast.makeText(this, R.string.offline_toast_config_missing, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, OfflineFormConfigDetailActivity.class);
        intent.putExtra(OfflineFormConfigDetailActivity.EXTRA_PATTERN_ID, _patternId);
        startActivity(intent);
    }

    private String formatRecordTime(long time) {
        if (time <= 0) {
            return getString(R.string.offline_text_unknown_record_time);
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(time));
    }

    private boolean hasPatternId() {
        return _patternId != null && !_patternId.isEmpty();
    }

}
