package com.huozige.lab.container;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.text.InputType;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huozige.lab.container.proxy.support.offlinecustomform.FormAdapter;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.BaseFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.SelectFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.model.TextFormItem;
import com.huozige.lab.container.proxy.support.realm.LocalKvHelper;

import com.huozige.lab.container.utilities.JsonFileHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CustomFormActivity extends AppCompatActivity {

    private RecyclerView _recyclerView;
    private FormAdapter _adapter;
    private Button _btnSubmit;
    private Intent _intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_form_activity);

        _intent = getIntent();

        initViews();
        setupRecyclerView();
        // loadFormData();
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

    private void loadFormData() {
        List<BaseFormItem> formItems = new ArrayList<>();

        String title = _intent.getStringExtra("title");

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

        _adapter.setFormItems(formItems);
    }

    private void loadFormDataFromJson() {

        String patternId = _intent.getStringExtra("patternId");

        //JSONObject jsonObject = JsonFileHelper.readJsonToExternalStorage(this, JsonFileHelper.FILE_NAME_OFFLINE_FORM, patternId);
        JSONObject jsonObject = JsonFileHelper.readTemplateJson();

        List<BaseFormItem> formItems = new ArrayList<>();

        try {
            if (!Objects.equals(patternId, jsonObject.getString("patternId"))) return;

            JSONArray customForm = jsonObject.getJSONArray(JsonFileHelper.FILE_FLAG_OFFLINE_FORM);

            for (int i = 0; i < customForm.length(); i++) {

                JSONObject formItem = customForm.getJSONObject(i);
                formItems.add(buildFormItem(formItem));

            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error parsing form data: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        _adapter.setFormItems(formItems);
    }

    private void setupListeners() {
        _btnSubmit.setOnClickListener(v -> onSubmit());
    }

    private void onSubmit() {
        // 验证表单
        if (_adapter.validateAll()) {
            // 收集数据
            Map<String, String> formData = _adapter.collectFormData();

            LocalKvHelper.upsertVRange(formData, null);

            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "请检查表单输入", Toast.LENGTH_SHORT).show();
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

    private BaseFormItem buildFormItem(JSONObject jsonObject) {

        try {


            String itemType = jsonObject.getString("itemType");

            switch (itemType) {
                case "textItem":
                    return buildTextFormItem(jsonObject);
                case "passwordItem":
                    return buildPasswordFormItem(jsonObject);
                case "selectItem":
                    return buildSelectFormItem(jsonObject);
                default:
                    throw new IllegalArgumentException("Unknown item type: " + itemType);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private TextFormItem buildTextFormItem(JSONObject jsonObject) {

        try {

            String itemId = jsonObject.getString("id");
            String title = jsonObject.getString("title");
            String hint = jsonObject.getString("hint");
            boolean required = jsonObject.getBoolean("required");

            TextFormItem textItem = new TextFormItem(itemId, title, hint, required);

            if (jsonObject.has("value")) {
                textItem.setValue(jsonObject.getString("value"));
            }

            JSONObject options = jsonObject.getJSONObject("options");

            if (options.has("minLength")) {
                textItem.setMinLength(options.getInt("minLength"));
            }

            if (options.has("maxLength")) {
                textItem.setMaxLength(options.getInt("maxLength"));
            }

            if (options.has("regexPattern")) {
                textItem.setRegexPattern(options.getString("regexPattern"));
            }

            return textItem;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    private TextFormItem buildPasswordFormItem(JSONObject jsonObject) {
        TextFormItem textItem = buildTextFormItem(jsonObject);
        textItem.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        return textItem;
    }

    private SelectFormItem buildSelectFormItem(JSONObject jsonObject) {

        try {

            String itemId = jsonObject.getString("id");
            String title = jsonObject.getString("title");
            String hint = jsonObject.getString("hint");
            boolean required = jsonObject.getBoolean("required");

            SelectFormItem selectItem = new SelectFormItem(itemId, title, hint, required);

            if (jsonObject.has("value")) {
                selectItem.setSelectedValue(jsonObject.getString("value"));
            }

            JSONObject options = jsonObject.getJSONObject("options");
            JSONArray selectOptions = options.getJSONArray("selectOptions");
            for (int i = 0; i < selectOptions.length(); i++) {

                JSONObject selectOption = selectOptions.getJSONObject(i);
                selectItem.addOption(selectOption.getString("value"), selectOption.getString("label"));

            }

            return selectItem;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
