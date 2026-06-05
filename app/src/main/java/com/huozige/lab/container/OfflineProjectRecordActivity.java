package com.huozige.lab.container;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineFormRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OfflineProjectRecordActivity extends BaseActivity {
    private static final int MENU_ID_CONFIG_MANAGE = 1;

    private String _patternId;
    private String _title;
    private String _description;
    private String _schemaVersion;
    private LinearLayout _recordListLayout;
    private TextView _emptyRecordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_project_record_activity);

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

        List<OfflineFormRecord> records = OfflineFormFileHelper.readRecords(this, _patternId);
        _emptyRecordTextView.setVisibility(records.isEmpty() ? View.VISIBLE : View.GONE);

        for (OfflineFormRecord record : records) {
            _recordListLayout.addView(createRecordCard(record));
        }
    }

    private View createRecordCard(OfflineFormRecord record) {
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        cardParams.setMargins(0, dp(8), 0, dp(8));
        cardView.setLayoutParams(cardParams);
        cardView.setRadius(dp(8));
        cardView.setCardElevation(dp(2));
        cardView.setUseCompatPadding(true);

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(16), dp(14), dp(16), dp(14));

        TextView title = new TextView(this);
        title.setText(formatRecordTime(record.getUpdatedAt()));
        title.setTextColor(ContextCompat.getColor(this, android.R.color.black));
        title.setTextSize(16);
        title.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView summary = new TextView(this);
        summary.setText(getString(R.string.offline_text_record_summary, record.getSchemaVersion(), record.getValues().size()));
        summary.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray));
        summary.setTextSize(13);

        content.addView(title);
        content.addView(summary);
        cardView.addView(content);
        return cardView;
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
