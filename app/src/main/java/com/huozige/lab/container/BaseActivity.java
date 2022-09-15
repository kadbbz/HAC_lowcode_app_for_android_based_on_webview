package com.huozige.lab.container;

import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    /**
     * 配置管理操作接口
     */
    ConfigManager configManager = new ConfigManager(this);

    @Override
    public void onResume() {
        super.onResume();

        // 设置动作栏
        ActionBar actionBar = getSupportActionBar();

        if(null!=actionBar){

            // 设置标题栏颜色
            actionBar.setBackgroundDrawable(new ColorDrawable(configManager.GetTCD()));
        }

        // 设置状态栏颜色，更美观
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(configManager.GetTCD());
    }
}
