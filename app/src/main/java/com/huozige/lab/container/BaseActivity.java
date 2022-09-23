package com.huozige.lab.container;

import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;
import com.huozige.lab.container.webview.ConfigManager;

import java.util.List;

/**
 * HAC所有页面的基类，提供权限申请、操作栏颜色配置等功能
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * 配置管理操作接口
     */
    protected ConfigManager ConfigManager = new ConfigManager(this);

    /**
     * 刷新操作栏颜色
     */
    public void refreshActionBarsColor(){

        // 需要支持跨线程调用
        this.runOnUiThread(() -> {

            // 设置动作栏
            ActionBar actionBar = getSupportActionBar();

            if(null!=actionBar){

                // 设置标题栏颜色
                actionBar.setBackgroundDrawable(new ColorDrawable(ConfigManager.getTCD()));
            }

            // 设置状态栏颜色，更美观
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ConfigManager.getTCD());
        });

    }

    /**
     * 每次恢复时，都需要重设颜色
     */
    @Override
    public void onResume() {
        super.onResume();

        refreshActionBarsColor();
    }

    /**
     * 申请特定的敏感权限
     * @param permission 敏感权限
     */
    public  void requirePermission(String permission){
        XXPermissions.with(this)
                .permission(permission)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (!all) {
                            Toast.makeText(BaseActivity.this,BaseActivity.this.getString(R.string.ui_toast_permissions_denied),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            Toast.makeText(BaseActivity.this,BaseActivity.this.getString(R.string.ui_toast_permissions_denied_never),Toast.LENGTH_LONG).show();
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(BaseActivity.this, permissions);
                        } else {
                            Toast.makeText(BaseActivity.this,BaseActivity.this.getString(R.string.ui_toast_permissions_denied),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
