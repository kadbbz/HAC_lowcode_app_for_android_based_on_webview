package com.huozige.lab.container.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;
import com.huozige.lab.container.R;

import java.util.List;

/**
 * 权限相关的帮助类
 */
public class PermissionsUtility {

    static final String LOG_TAG="HAC_LifecycleUtility";

    /**
     * 申请特定的敏感权限
     * @param context 操作的上下文（有界面）
     * @param permission 敏感权限
     */
    public static void requirePermission(Context context, String permission){

        Log.v(LOG_TAG,"开始申请权限："+permission);

        XXPermissions.with(context)
                .permission(permission)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (!all) {
                            Log.w(LOG_TAG,"申请的权限中部分成功，部分失败，已提示给用户："+permission);

                            Toast.makeText(context,context.getString(R.string.ui_toast_permissions_denied),Toast.LENGTH_LONG).show();
                        }else{
                            Log.v(LOG_TAG,"权限已成功申请："+permission);
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {

                            Log.e(LOG_TAG,"权限申请被拒绝且勾选为never，即将导航到系统的权限设置页面："+permission);

                            Toast.makeText(context,context.getString(R.string.ui_toast_permissions_denied_never),Toast.LENGTH_LONG).show();
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(context, permissions);
                        } else {

                            Log.e(LOG_TAG,"权限申请被拒绝，已提示给用户："+permission);

                            Toast.makeText(context,context.getString(R.string.ui_toast_permissions_denied),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
