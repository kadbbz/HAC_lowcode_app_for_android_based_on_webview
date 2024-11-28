package com.huozige.lab.container;

import androidx.appcompat.app.AppCompatActivity;

import com.huozige.lab.container.utilities.UiUtility;

/**
 * HAC所有页面的基类，提供权限申请、操作栏颜色配置等功能
 */
public class BaseActivity extends AppCompatActivity {

    public BaseActivity() {
        super();
    }

    /**
     * 刷新操作栏颜色
     */
    public void refreshActionBar() {

        // 需要支持跨线程调用
        this.runOnUiThread(() -> {

            // 设置动作栏
            UiUtility.initializeActionBar(this, getSupportActionBar(), false);

        });

    }

    /**
     * 每次恢复时，都需要重设颜色
     */
    @Override
    protected void onResume() {
        super.onResume();

        refreshActionBar();
    }
}
