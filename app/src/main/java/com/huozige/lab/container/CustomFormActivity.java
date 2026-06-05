package com.huozige.lab.container;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.proxy.support.offlinecustomform.FormAdapter;
import com.huozige.lab.container.proxy.support.offlinecustomform.helper.OfflineFormFileHelper;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.BaseFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineFormDefinitionFile;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.OfflineFormRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomFormActivity extends AppCompatActivity {

    private RecyclerView _recyclerView;
    private FormAdapter _adapter;
    private Button _btnSubmit;
    private Intent _intent;
    private boolean _formLoaded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_form_activity);

        _intent = getIntent();

        initViews();
        setupRecyclerView();
        loadFormDataFromJson();
        setupListeners();
    }

    private void initViews() {
        _recyclerView = findViewById(R.id.recycler_view);
        _btnSubmit = findViewById(R.id.btn_submit);
    }

    private void setupRecyclerView() {
        _adapter = new FormAdapter();
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
        _recyclerView.setAdapter(_adapter);

        // 添加item间距
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                _recyclerView.getContext(),
                LinearLayoutManager.VERTICAL
        );

        _recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void loadFormDataFromJson() {

        String patternId = _intent.getStringExtra("patternId");
        List<BaseFormItem> formItems = new ArrayList<>();
        if (patternId == null || patternId.isEmpty()) {
            Toast.makeText(this, R.string.offline_toast_config_missing, Toast.LENGTH_SHORT).show();
            _adapter.setFormItems(formItems);
            return;
        }

        try {
            OfflineFormDefinitionFile definitionFile = OfflineFormFileHelper.readDefinition(this, patternId);
            if (definitionFile != null && patternId.equals(definitionFile.getJsonSchema().getPatternId())) {
                formItems = definitionFile.getJsonSchema().getFormItems();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.offline_toast_parse_form_failed, e.getMessage()), Toast.LENGTH_LONG).show();
        }
        _formLoaded = !formItems.isEmpty();
        _adapter.setFormItems(formItems);
    }

    private void setupListeners() {
        _btnSubmit.setOnClickListener(v -> onSubmit());
    }

    private void onSubmit() {
        if (!_formLoaded) {
            Toast.makeText(this, R.string.offline_toast_config_missing, Toast.LENGTH_SHORT).show();
            return;
        }

        // 验证表单
        if (_adapter.validateAll()) {
            // 收集数据
            Map<String, String> formData = _adapter.collectFormData();
            String patternId = _intent.getStringExtra("patternId");
            String schemaVersion = _intent.getStringExtra("schemaVersion");
            OfflineFormRecord record = OfflineFormRecord.createSubmitted(patternId, schemaVersion, formData);
            // 离线填报数据按表单定义目录保存，删除表单定义时可直接清理对应文件夹。
            OfflineFormFileHelper.writeRecord(this, record);


            Toast.makeText(this, R.string.offline_toast_record_saved, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, R.string.offline_toast_check_form_input, Toast.LENGTH_SHORT).show();
            // 滚动到第一个错误项
            scrollToFirstError();
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
