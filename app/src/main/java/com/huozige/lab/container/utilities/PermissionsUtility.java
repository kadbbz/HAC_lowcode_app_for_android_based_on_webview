package com.huozige.lab.container.utilities;

import android.content.Context;
import com.elvishew.xlog.XLog;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;
import com.huozige.lab.container.R;

import java.util.List;

/**
 * 权限相关的帮助类
 */
public class PermissionsUtility {

    static final String LOG_TAG="HAC_PermissionsUtility";

    /**
     * 申请特定的敏感权限
     * @param context 操作的上下文（有界面）
     * @param permissions 本次申请的敏感权限
     * @param successAction 成功申请权限后的动作
     */
    public static void asyncRequirePermissions(Context context, String[] permissions, Runnable successAction){

        XLog.v(LOG_TAG,"开始申请权限："+String.join(",", permissions));

        XXPermissions.with(context)
                .permission(permissions)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(@NonNull List<String> permissions, boolean all) {
                        if (!all) {
                            XLog.v(LOG_TAG,"申请的权限中部分成功，部分失败，已提示给用户");

                            Toast.makeText(context,context.getString(R.string.ui_toast_permissions_denied),Toast.LENGTH_LONG).show();
                        }else{
                            XLog.v(LOG_TAG,"权限已成功申请，执行回调");

                            successAction.run();
                        }
                    }

                    @Override
                    public void onDenied(@NonNull List<String> permissions, boolean never) {
                        if (never) {

                            XLog.e("["+LOG_TAG+ "]权限申请被拒绝且勾选为never，即将导航到系统的权限设置页面");

                            Toast.makeText(context,context.getString(R.string.ui_toast_permissions_denied_never),Toast.LENGTH_LONG).show();
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(context, permissions);
                        } else {

                            XLog.e("["+LOG_TAG+ "]权限申请被拒绝，已提示给用户");

                            Toast.makeText(context,context.getString(R.string.ui_toast_permissions_denied),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
