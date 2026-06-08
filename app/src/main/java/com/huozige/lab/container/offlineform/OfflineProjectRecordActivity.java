package com.huozige.lab.container.offlineform;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.huozige.lab.container.BaseActivity;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;
import com.huozige.lab.container.offlineform.model.OfflineFormRecordStatus;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

public class OfflineProjectRecordActivity extends BaseActivity {
    private static final int MENU_ID_CONFIG_MANAGE = 1;
    private static final int MENU_ID_DRAFT_CONTINUE = 1;
    private static final int MENU_ID_DRAFT_DELETE = 2;

    private String _patternId;
    private String _title;
    private String _description;
    private String _schemaVersion;
    private LinearLayout _draftListLayout;
    private LinearLayout _recordListLayout;
    // 已导出记录表实例挂载的容器。
    private LinearLayout _exportedRecordListLayout;
    private TextView _emptyRecordTextView;
    // 已导出记录一级标题；没有已导出数据时隐藏。
    private TextView _exportedRecordTitleTextView;
    private OfflineRecordTableView _submittedRecordTable;
    private OfflineRecordTableView _exportedRecordTable;

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
        _exportedRecordListLayout = findViewById(R.id.exportedRecordListLayout);
        _emptyRecordTextView = findViewById(R.id.emptyRecordTextView);
        _exportedRecordTitleTextView = findViewById(R.id.exportedRecordTitleTextView);
        _submittedRecordTable = new OfflineRecordTableView(this, _recordListLayout, true, createRecordTableCallback());
        _exportedRecordTable = new OfflineRecordTableView(this, _exportedRecordListLayout, false, createRecordTableCallback());

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
        if (_recordListLayout == null || _exportedRecordListLayout == null) {
            return;
        }

        _submittedRecordTable.clear();
        _exportedRecordTable.clear();
        _exportedRecordTitleTextView.setVisibility(View.GONE);
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
        List<OfflineFormRecord> submittedRecords = new ArrayList<>();
        List<OfflineFormRecord> exportedRecords = new ArrayList<>();
        for (OfflineFormRecord record : OfflineFormFileHelper.readRecords(this, _patternId)) {
            if (record.getStatus() == OfflineFormRecordStatus.DRAFT) {
                draftRecords.add(record);
            } else if (record.getStatus() == OfflineFormRecordStatus.EXPORTED) {
                exportedRecords.add(record);
            } else if (record.getStatus() == OfflineFormRecordStatus.SUBMITTED) {
                submittedRecords.add(record);
            }
        }

        renderDraftRecords(draftRecords, definitionFile.getJsonSchema().getSchemaVersion());
        _emptyRecordTextView.setVisibility(submittedRecords.isEmpty() && exportedRecords.isEmpty() ? View.VISIBLE : View.GONE);
        if (!submittedRecords.isEmpty()) {
            _submittedRecordTable.setRecords(definitionFile, submittedRecords);
        } else {
            _submittedRecordTable.clear();
        }
        if (!exportedRecords.isEmpty()) {
            _exportedRecordTitleTextView.setVisibility(View.VISIBLE);
            _exportedRecordTable.setRecords(definitionFile, exportedRecords);
        } else {
            _exportedRecordTable.clear();
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

    private void openExportedRecordDetail(OfflineFormRecord record) {
        Intent intent = new Intent(this, OfflineExportedRecordDetailActivity.class);
        intent.putExtra(OfflineExportedRecordDetailActivity.EXTRA_PATTERN_ID, _patternId);
        intent.putExtra(OfflineExportedRecordDetailActivity.EXTRA_RECORD_ID, record.getRecordId());
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

    private void showConfigDetail() {
        if (!hasPatternId()) {
            Toast.makeText(this, R.string.offline_toast_config_missing, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, OfflineFormConfigDetailActivity.class);
        intent.putExtra(OfflineFormConfigDetailActivity.EXTRA_PATTERN_ID, _patternId);
        startActivity(intent);
    }

    private boolean hasPatternId() {
        return _patternId != null && !_patternId.isEmpty();
    }

    private String formatRecordTime(long time) {
        if (time <= 0) {
            return getString(R.string.offline_text_unknown_record_time);
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(time));
    }

    private OfflineRecordTableView.Callback createRecordTableCallback() {
        return new OfflineRecordTableView.Callback() {
            @Override
            public void onEditRecord(OfflineFormRecord record, String currentSchemaVersion) {
                openRecord(record, currentSchemaVersion);
            }

            @Override
            public void onViewRecord(OfflineFormRecord record) {
                openExportedRecordDetail(record);
            }

            @Override
            public void onDeleteRecord(OfflineFormRecord record) {
                confirmDeleteRecord(record);
            }
        };
    }

}
