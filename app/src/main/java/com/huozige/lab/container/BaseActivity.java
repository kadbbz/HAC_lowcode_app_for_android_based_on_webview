package com.huozige.lab.container;

import android.graphics.Color;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.huozige.lab.container.utilities.ColorUtility;
import com.huozige.lab.container.utilities.ConfigManager;

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
            ActionBar actionBar = getSupportActionBar();

            if (actionBar != null) {
                if (!ConfigManager.getInstance().getActionBarVisible()) {
                    // 隐藏ActionBar
                    actionBar.hide();
                } else {

                    // 设置状态栏颜色，做沉浸式体验
                    Window window = this.getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.setStatusBarColor(ConfigManager.getInstance().getTCD());

                    // 设置ActionBar的颜色，需要避免文字和背景颜色过于接近
                    Toolbar aBar = findViewById(androidx.appcompat.R.id.action_bar);

                    if (aBar != null) {

                        aBar.setBackgroundColor(ConfigManager.getInstance().getTCD());
                        // 深色采用黑色文字，浅色用白色文字
                        if (ColorUtility.getGrayScale(ConfigManager.getInstance().getTCD()) > ColorUtility.GRAYSCALE_THRESHOLD_BRIGHT_COLOR) {
                            aBar.setTitleTextColor(Color.BLACK);
                        } else {
                            aBar.setTitleTextColor(Color.WHITE);
                        }

                    }
                }
            }

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
