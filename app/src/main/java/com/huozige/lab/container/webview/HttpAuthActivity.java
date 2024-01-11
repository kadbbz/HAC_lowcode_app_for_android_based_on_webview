package com.huozige.lab.container.webview;

import android.content.Intent;
import android.os.Bundle;
import com.elvishew.xlog.XLog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.huozige.lab.container.BaseActivity;
import com.huozige.lab.container.R;
import com.huozige.lab.container.SettingActivity;
import com.huozige.lab.container.utilities.ConfigManager;

public class HttpAuthActivity extends BaseActivity {
    static final String LOG_TAG = "HAC_HttpAuthActivity";
    static  final String BUNDLE_EXTRA_RESULT_USER="username";
    static  final String BUNDLE_EXTRA_RESULT_PASSWORD="password";
    EditText _txtUser, _txtPassword;
    CheckBox _chkRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_auth);

        setTitle(getString(R.string.ui_title_auth));

        _txtUser = findViewById(R.id.txtUser);
        _txtPassword = findViewById(R.id.txtPwd);
        _chkRemember = findViewById(R.id.chkRemember);

        Button cmdSave = findViewById(R.id.cmdSaveToken);
        cmdSave.setOnClickListener(save);

        Button cmdGotoSetting = findViewById(R.id.cmdGotoSetting);
        cmdGotoSetting.setOnClickListener(goSetting);

    }

    @Override
    public void onResume() {

        super.onResume();

        // 初始化
        // 考虑到企业用移动设备的典型应用场景（设备与应用系统和用户绑定），同一设备上的用户名趋同，所以仅记录一份。
        _txtUser.setText(ConfigManager.getInstance().getUserName());
        _txtPassword.setText(ConfigManager.getInstance().getPassword());
    }

    View.OnClickListener save = view -> {

        XLog.v(LOG_TAG,"用户提供了认证信息。");

        if(_chkRemember.isChecked()){
            // 将用户输入的信息保存到配置中
            ConfigManager.getInstance().upsertStringEntry(ConfigManager.PREFERENCE_KEY_CURRENT_USER, _txtUser.getText().toString());
            ConfigManager.getInstance().upsertStringEntry(ConfigManager.PREFERENCE_KEY_CURRENT_PWD, _txtPassword.getText().toString());
            XLog.v(LOG_TAG,"认证信息已保存到本地存储。");
        }
        // 将其打包发给调用者
        Intent res = new Intent();
        res.putExtra(BUNDLE_EXTRA_RESULT_USER, _txtUser.getText().toString());
        res.putExtra(BUNDLE_EXTRA_RESULT_PASSWORD, _txtPassword.getText().toString());
        setResult(0, res);

        finish();
    };

    View.OnClickListener goSetting = view -> startActivity(new Intent(HttpAuthActivity.this, SettingActivity.class));
}