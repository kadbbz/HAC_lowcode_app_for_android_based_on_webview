package com.huozige.lab.container.offlineform;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.huozige.lab.container.BaseActivity;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.OfflineFormRecord;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;

public class OfflineExportedRecordDetailActivity extends BaseActivity {
    public static final String EXTRA_PATTERN_ID = "patternId";
    public static final String EXTRA_RECORD_ID = "recordId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.offline_title_exported_record_detail);
        setContentView(R.layout.offline_exported_record_detail_activity);

        String patternId = getIntent().getStringExtra(EXTRA_PATTERN_ID);
        String recordId = getIntent().getStringExtra(EXTRA_RECORD_ID);
        OfflineFormRecord record = OfflineFormFileHelper.readRecord(this, patternId, recordId);
        if (record == null) {
            Toast.makeText(this, R.string.offline_toast_record_missing, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        TextView recordJsonTextView = findViewById(R.id.recordJsonTextView);
        recordJsonTextView.setText(JSON.toJSONString(record, SerializerFeature.PrettyFormat));
    }
}
