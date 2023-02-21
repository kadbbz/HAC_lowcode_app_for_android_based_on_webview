package com.huozige.lab.container.webview;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.huozige.lab.container.BaseActivity;
import com.huozige.lab.container.QuickConfigActivity;
import com.huozige.lab.container.R;
import com.huozige.lab.container.SettingActivity;
import com.huozige.lab.container.utilities.LifecycleUtility;

public class HttpAuthActivity extends BaseActivity {

    static final String LOG_TAG = "HAC_HttpAuthActivity";

    EditText _txtUser, _txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_auth);

        setTitle(getString(R.string.ui_title_auth));

        _txtUser = findViewById(R.id.txtUser);
        _txtPassword = findViewById(R.id.txtPwd);

        Button cmdSave = findViewById(R.id.cmdSaveToken);
        cmdSave.setOnClickListener(save);

        Button cmdGotoSetting = findViewById(R.id.cmdGotoSetting);
        cmdGotoSetting.setOnClickListener(goSetting);

    }

    @Override
    public void onResume() {

        super.onResume();

        // 初始化
        _txtUser.setText(getConfigManager().getUserName());
        _txtPassword.setText(getConfigManager().getPassword());
    }

    View.OnClickListener save = view -> {

        // 直接将用户输入的信息保存到配置中
        getConfigManager().upsertUserName(_txtUser.getText().toString());
        getConfigManager().upsertPassword(_txtPassword.getText().toString());
        Log.v(LOG_TAG,"用户提供了认证信息，已更新到本地存储。");
        finish();

    };

    View.OnClickListener goSetting = view -> startActivity(new Intent(HttpAuthActivity.this, SettingActivity.class));
}