package com.huozige.lab.container.utilities;


import android.app.Activity;
import android.graphics.Color;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

/**
 * 界面相关的帮助类
 */
public class UiUtility {

    /**
     * 初始化ActionBar
     * @param context ActionBar所在的页面
     * @param actionBar ActionBar的实例，如果传入为Null则不做任何事情
     * @param shouldShowAnyway 是否忽略配置，强制显示ActionBar
     */
    public static void initializeActionBar(Activity context, ActionBar actionBar, boolean shouldShowAnyway){
        if (actionBar != null) {

            if (!shouldShowAnyway && !ConfigManager.getInstance().getActionBarVisible()) {
                // 隐藏ActionBar
                actionBar.hide();
            } else {

                // 设置状态栏颜色，做沉浸式体验
                Window window = context.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(ConfigManager.getInstance().getTCD());

                // 设置ActionBar的颜色，需要避免文字和背景颜色过于接近
                Toolbar aBar = context.findViewById(androidx.appcompat.R.id.action_bar);

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
    }
}
