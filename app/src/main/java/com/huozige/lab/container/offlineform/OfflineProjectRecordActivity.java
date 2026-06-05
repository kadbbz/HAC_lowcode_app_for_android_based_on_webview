package com.huozige.lab.container.offlineform;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
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
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;
import com.huozige.lab.container.offlineform.model.formitem.BaseFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OfflineProjectRecordActivity extends BaseActivity {
    private static final int MENU_ID_CONFIG_MANAGE = 1;
    private static final int MENU_ID_RECORD_EDIT = 1;
    private static final int MENU_ID_RECORD_DELETE = 2;

    private String _patternId;
    private String _title;
    private String _description;
    private String _schemaVersion;
    private LinearLayout _recordListLayout;
    private TextView _emptyRecordTextView;

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

        List<OfflineFormRecord> records = OfflineFormFileHelper.readRecords(this, _patternId);
        _emptyRecordTextView.setVisibility(records.isEmpty() ? View.VISIBLE : View.GONE);
        if (!records.isEmpty()) {
            _recordListLayout.addView(createRecordTable(records, definitionFile));
        }
    }

    private View createRecordTable(List<OfflineFormRecord> records, OfflineFormDefinitionFile definitionFile) {
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        cardParams.setMargins(0, dp(4), 0, dp(8));
        cardView.setLayoutParams(cardParams);
        cardView.setCardBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        cardView.setRadius(dp(8));
        cardView.setCardElevation(dp(2));
        cardView.setUseCompatPadding(true);
        cardView.setContentPadding(dp(8), dp(8), dp(8), dp(8));

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
                dp(44),
                LinearLayout.LayoutParams.WRAP_CONTENT));
        actionTableLayout.setShrinkAllColumns(false);
        actionTableLayout.setStretchAllColumns(false);

        List<String> displayColumns = getDisplayColumns(definitionFile);
        Map<String, String> columnTitles = buildColumnTitles(definitionFile);
        String currentSchemaVersion = definitionFile.getJsonSchema().getSchemaVersion();

        dataTableLayout.addView(createHeaderRow(displayColumns, columnTitles));
        actionTableLayout.addView(createActionHeaderRow());
        for (OfflineFormRecord record : records) {
            dataTableLayout.addView(createRecordRow(record, displayColumns, currentSchemaVersion));
            actionTableLayout.addView(createActionRow(record, currentSchemaVersion));
        }

        scrollView.addView(dataTableLayout);
        tableContainer.addView(scrollView);
        tableContainer.addView(actionTableLayout);
        cardView.addView(tableContainer);
        return cardView;
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

    private TableRow createRecordRow(OfflineFormRecord record, List<String> displayColumns, String currentSchemaVersion) {
        TableRow row = new TableRow(this);
        row.setClickable(true);
        row.setBackgroundResource(android.R.drawable.list_selector_background);
        for (String fieldId : displayColumns) {
            row.addView(createCell(getRecordValue(record, fieldId), false));
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
        textView.setMinWidth(dp(120));
        textView.setMaxWidth(dp(220));
        textView.setPadding(dp(12), dp(10), dp(12), dp(10));
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

    private View createActionCell(OfflineFormRecord record, String currentSchemaVersion) {
        LinearLayout actions = new LinearLayout(this);
        actions.setOrientation(LinearLayout.HORIZONTAL);
        actions.setPadding(dp(4), dp(2), dp(4), dp(2));
        actions.setMinimumWidth(dp(24));

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
        button.setPadding(dp(6), dp(8), dp(6), dp(8));
        button.setMinWidth(dp(36));
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

    private Map<String, String> buildColumnTitles(OfflineFormDefinitionFile definitionFile) {
        Map<String, String> titles = new HashMap<>();
        if (definitionFile.getJsonSchema() == null || definitionFile.getJsonSchema().getFormItems() == null) {
            return titles;
        }
        for (BaseFormItem formItem : definitionFile.getJsonSchema().getFormItems()) {
            titles.put(formItem.getId(), formItem.getTitle());
        }
        return titles;
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

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
