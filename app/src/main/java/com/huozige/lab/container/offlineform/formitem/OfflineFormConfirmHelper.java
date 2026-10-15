package com.huozige.lab.container.offlineform.formitem;

import android.content.Context;
import android.text.TextUtils;

import androidx.appcompat.app.AlertDialog;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.common.FormItemConfirmOptions;

/**
 * 字段“修改前二次确认”弹窗的统一入口。
 *
 * <p>仅负责展示弹窗与派发结果，绝不直接改动字段模型：调用方在 {@code onConfirm}
 * 中才写入新值并更新时间，取消则通过 {@code onCancel} 决定是否回滚界面。</p>
 */
public final class OfflineFormConfirmHelper {

    private OfflineFormConfirmHelper() {
    }

    public static boolean isEnabled(BaseFormItem item) {
        FormItemConfirmOptions options = item == null ? null : item.getConfirmOptions();
        return options != null && options.isEnabled();
    }

    /**
     * 弹出二次确认。
     *
     * @param context         弹窗上下文（通常为 itemView.getContext()）
     * @param item            字段模型，用于取配置与占位符
     * @param newDisplayValue 新值的展示文本，用于确认提示
     * @param onConfirm       确认回调（只有确认时触发）
     * @param onCancel        取消回调（点取消、返回键、点外部取消时触发），可为 null
     */
    public static void show(Context context, BaseFormItem item, String newDisplayValue,
                            Runnable onConfirm, Runnable onCancel) {
        FormItemConfirmOptions options = item == null ? null : item.getConfirmOptions();
        if (options == null || !options.isEnabled()) {
            if (onConfirm != null) {
                onConfirm.run();
            }
            return;
        }

        String title = resolve(options.getTitle(), context.getString(R.string.offline_confirm_default_title));
        String messageTemplate = resolve(options.getMessage(), context.getString(R.string.offline_confirm_default_message));
        String message = messageTemplate
                .replace("{title}", safe(item.getTitle()))
                .replace("{value}", safe(newDisplayValue))
                .replace("{oldValue}", safe(item.getValue()));

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(resolve(options.getConfirmButtonText(),
                        context.getString(R.string.offline_button_confirm)), (dialog, which) -> {
                    if (onConfirm != null) {
                        onConfirm.run();
                    }
                })
                .setNegativeButton(resolve(options.getCancelButtonText(),
                        context.getString(R.string.offline_button_cancel)), (dialog, which) -> {
                    if (onCancel != null) {
                        onCancel.run();
                    }
                })
                .setOnCancelListener(dialog -> {
                    if (onCancel != null) {
                        onCancel.run();
                    }
                })
                .show();
    }

    private static String resolve(String custom, String fallback) {
        return TextUtils.isEmpty(custom) ? fallback : custom;
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }
}
