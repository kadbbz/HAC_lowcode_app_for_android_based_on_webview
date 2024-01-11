package com.huozige.lab.container;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.huozige.lab.container.utilities.ConfigManager;

/**
 * 扫码配置的替代页面：输入JSON文本实现配置
 */
public class TextConfigActivity extends BaseActivity {

    EditText _txtConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_config);

        setTitle(getString(R.string.ui_title_quick_config));

        _txtConfig = findViewById(R.id.editTextConfig);

        Button cmdSave = findViewById(R.id.cmdTCSave);
        cmdSave.setOnClickListener(save);
    }

    View.OnClickListener save = view -> {

        String json = _txtConfig.getText().toString();

        AlertDialog.Builder ab = new AlertDialog.Builder(TextConfigActivity.this);
        ab.setPositiveButton(TextConfigActivity.this.getString(R.string.ui_button_ok), (dialogInterface, i) -> {

            // 执行配置过程
            Boolean isOk= ConfigManager.getInstance().quickConfig(json);

            if(isOk){

                // 提示正确的信息
                Toast.makeText(TextConfigActivity.this, getString(R.string.ui_message_quick_config_done), Toast.LENGTH_LONG).show();

                // 返回
                finish();
            }else{

                // 仅提示错误信息
                Toast.makeText(TextConfigActivity.this, getString(R.string.ui_message_quick_config_broken), Toast.LENGTH_LONG).show();
            }

        }).setNegativeButton(TextConfigActivity.this.getString(R.string.ui_button_cancel), (dialogInterface, i) -> {
            // 什么都不干
        });

        ab.setMessage(R.string.ui_message_quick_config_confirm);
        ab.setTitle(R.string.ui_menu_settings);
        ab.show();

    };
}