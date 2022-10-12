package com.huozige.lab.container.utilities;

import android.content.Context;
import android.widget.Toast;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;
import com.huozige.lab.container.R;

import java.util.List;

/**
 * 权限相关的帮助类
 */
public class PermissionsUtility {

    /**
     * 申请特定的敏感权限
     * @param context 操作的上下文（有界面）
     * @param permission 敏感权限
     */
    public static void requirePermission(Context context, String permission){
        XXPermissions.with(context)
                .permission(permission)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (!all) {
                            Toast.makeText(context,context.getString(R.string.ui_toast_permissions_denied),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            Toast.makeText(context,context.getString(R.string.ui_toast_permissions_denied_never),Toast.LENGTH_LONG).show();
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(context, permissions);
                        } else {
                            Toast.makeText(context,context.getString(R.string.ui_toast_permissions_denied),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
