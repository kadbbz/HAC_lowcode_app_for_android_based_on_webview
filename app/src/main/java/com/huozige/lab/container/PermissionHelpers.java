package com.huozige.lab.container;

import android.app.Activity;
import android.widget.Toast;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

public class PermissionHelpers {

    public static void RequirePermission(Activity context, String permission){
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
