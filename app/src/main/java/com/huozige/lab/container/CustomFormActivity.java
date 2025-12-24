package com.huozige.lab.container;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.proxy.support.offlinecustomform.FormAdapter;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.BaseFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.SelectFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.TextFormItem;
import com.huozige.lab.container.proxy.support.realm.LocalKvHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomFormActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FormAdapter adapter;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_form_activity);

        initViews();
        setupRecyclerView();
        loadFormData();
        setupListeners();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view);
        btnSubmit = findViewById(R.id.btn_submit);
    }

    private void setupRecyclerView() {
        adapter = new FormAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // 添加item间距
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                LinearLayoutManager.VERTICAL
        );

        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void loadFormData() {
        List<BaseFormItem> formItems = new ArrayList<>();

        String title = getIntent().getStringExtra("title");

        // 用户名
        TextFormItem usernameItem = new TextFormItem("username", "用户名", "请输入用户名", true);
        usernameItem.setMinLength(1);
        usernameItem.setMaxLength(20);
        usernameItem.setValue(title);
        formItems.add(usernameItem);

        // 密码
        TextFormItem passwordItem = new TextFormItem("password", "密码", "请输入密码", true);
        passwordItem.setInputType(android.text.InputType.TYPE_CLASS_TEXT |
                android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        formItems.add(passwordItem);

        // 邮箱
        TextFormItem emailItem = new TextFormItem("email", "邮箱", "请输入邮箱", true);
        emailItem.setRegexPattern("^[A-Za-z0-9+_.-]+@(.+)$");
        formItems.add(emailItem);

        // 性别选择
        SelectFormItem genderItem = new SelectFormItem("gender", "性别", "请选择性别", true);
        genderItem.addOption("male", "男");
        genderItem.addOption("female", "女");
        genderItem.addOption("other", "其他");
        formItems.add(genderItem);

        // 城市选择
        SelectFormItem cityItem = new SelectFormItem("city", "城市", "请选择城市", false);
        cityItem.addOption("beijing", "北京");
        cityItem.addOption("shanghai", "上海");
        cityItem.addOption("guangzhou", "广州");
        cityItem.addOption("shenzhen", "深圳");
        formItems.add(cityItem);

        adapter.setFormItems(formItems);
    }

    private void setupListeners() {
        btnSubmit.setOnClickListener(v -> onSubmit());
    }

    private void onSubmit() {
        // 验证表单
        if (adapter.validateAll()) {
            // 收集数据
            Map<String, String> formData = adapter.collectFormData();

            LocalKvHelper.upsertVRange(formData, null);

            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "请检查表单输入", Toast.LENGTH_SHORT).show();
            // 滚动到第一个错误项
            scrollToFirstError();
        }
    }

    private void scrollToFirstError() {
        List<BaseFormItem> items = adapter.getFormItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getErrorMessage() != null) {
                recyclerView.scrollToPosition(i);
                break;
            }
        }
    }
}
