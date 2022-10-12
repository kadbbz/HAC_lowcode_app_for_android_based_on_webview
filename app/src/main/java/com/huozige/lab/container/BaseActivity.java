package com.huozige.lab.container;

import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.huozige.lab.container.utilities.ConfigManager;

/**
 * HAC所有页面的基类，提供权限申请、操作栏颜色配置等功能
 */
public class BaseActivity extends AppCompatActivity {

    private ConfigManager configManager;

    public  BaseActivity(){
        super();

        configManager = new ConfigManager(this);
    }

    /**
     * 刷新操作栏颜色
     */
    public void refreshActionBar(){

        // 需要支持跨线程调用
        this.runOnUiThread(() -> {

            // 设置动作栏
            ActionBar actionBar = getSupportActionBar();

            if(actionBar!=null){
                if(!getConfigManager().getActionBarVisible()){
                    // 隐藏ActionBar
                    actionBar.hide();
                }else{

                    // 设置ActionBar的颜色
                    actionBar.setBackgroundDrawable(new ColorDrawable(getConfigManager().getTCD()));

                    // 设置状态栏颜色，做沉浸式体验
                    Window window = this.getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.setStatusBarColor(getConfigManager().getTCD());
                }
            }

        });

    }

    /**
     * 每次恢复时，都需要重设颜色
     */
    @Override
    public void onResume() {
        super.onResume();

        refreshActionBar();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }
}
