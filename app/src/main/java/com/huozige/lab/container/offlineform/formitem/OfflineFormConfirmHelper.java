package com.huozige.lab.container.offlineform.formitem;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.widget.Button;

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
        String messageTemplate = resolveMessageTemplate(context, item, options);
        String message = messageTemplate
                .replace("{title}", safe(item.getTitle()))
                .replace("{value}", safe(newDisplayValue))
                .replace("{oldValue}", safe(item.getDisplayValue()));

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(resolve(options.getConfirmButtonText(),
                        context.getString(R.string.offline_button_confirm)), (d, which) -> {
                    if (onConfirm != null) {
                        onConfirm.run();
                    }
                })
                .setNegativeButton(resolve(options.getCancelButtonText(),
                        context.getString(R.string.offline_button_cancel)), (d, which) -> {
                    if (onCancel != null) {
                        onCancel.run();
                    }
                })
                .setOnCancelListener(d -> {
                    if (onCancel != null) {
                        onCancel.run();
                    }
                })
                .create();
        dialog.show();
        styleButtons(dialog, context);
    }

    /**
     * 选择弹窗内容模板：
     * 1. 首次填写（当前无值）且配置了「首次填写内容」时优先使用；
     * 2. 否则使用「弹窗内容」；
     * 3. 两者都为空时使用默认文案；
     * 4. 当前无值、但自定义文案依赖 {oldValue} 时，回退到内置的首次填写文案，避免出现「」。
     */
    private static String resolveMessageTemplate(Context context, BaseFormItem item, FormItemConfirmOptions options) {
        String customMessage = options.getMessage();
        boolean firstFill = item.isEmpty();
        if (firstFill && !TextUtils.isEmpty(options.getFirstFillMessage())) {
            return options.getFirstFillMessage();
        }
        if (firstFill && !TextUtils.isEmpty(customMessage) && customMessage.contains("{oldValue}")) {
            return context.getString(R.string.offline_confirm_default_first_fill_message);
        }
        return resolve(customMessage, context.getString(R.string.offline_confirm_default_message));
    }

    /** 区分确定/取消按钮：确定高亮加粗，取消用次要色，避免两个按钮看起来一样。 */
    private static void styleButtons(AlertDialog dialog, Context context) {
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        if (positiveButton != null) {
            positiveButton.setTextColor(context.getColor(R.color.huozige_blue));
            positiveButton.setTypeface(null, Typeface.BOLD);
        }
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        if (negativeButton != null) {
            negativeButton.setTextColor(context.getColor(R.color.gray));
        }
    }

    private static String resolve(String custom, String fallback) {
        return TextUtils.isEmpty(custom) ? fallback : custom;
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }
}
