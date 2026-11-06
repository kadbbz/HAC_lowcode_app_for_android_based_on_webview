package com.huozige.lab.container.offlineform.formitem.signature;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.signature.SignatureFormItem;
import com.huozige.lab.container.offlineform.model.formitem.signature.SignatureFormItemValue;
import com.huozige.lab.container.offlineform.util.ODateUtils;
import com.huozige.lab.container.offlineform.util.OfflineSmartTime;
import com.huozige.lab.container.offlineform.util.Utils;

import java.io.File;

/**
 * 签名抽屉人员行与签名进度条的公共渲染逻辑。
 *
 * <p>人员行与表单字段同样的通栏排列：左侧姓名 + 状态 / 更新时间（智能时间），
 * 右侧签名预览（已签显示签名图，未签显示「去签名」占位）；点击预览区域签名 / 重签，
 * 不再单独放操作按钮。</p>
 */
final class SignatureUi {

    interface OnSignRequested {
        void onSignRequested(String userName);
    }

    private SignatureUi() {
    }

    static View createUserCard(Context context, SignatureFormItem signatureItem, String userName,
                               OnSignRequested listener) {
        View row = LayoutInflater.from(context)
                .inflate(R.layout.custom_form_item_signature_user_card, null, false);
        row.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        bindUserCard(row, context, signatureItem, userName, listener);
        return row;
    }

    static void bindUserCard(View card, Context context, SignatureFormItem signatureItem, String userName,
                             OnSignRequested listener) {
        TextView nameView = card.findViewById(R.id.signature_user_name);
        TextView statusView = card.findViewById(R.id.signature_user_status);
        TextView timeView = card.findViewById(R.id.signature_user_time);
        FrameLayout previewView = card.findViewById(R.id.signature_user_preview);
        ImageView imageView = card.findViewById(R.id.signature_user_image);
        View placeholderView = card.findViewById(R.id.signature_user_placeholder);

        SignatureFormItemValue signature = signatureItem.getSignature(userName);
        boolean signed = signature != null;
        nameView.setText(userName);

        if (signed) {
            statusView.setText(R.string.offline_text_signature_set);
            statusView.setTextColor(context.getColor(R.color.offline_form_ok));
            if (signature.getUpdateTime() != null) {
                timeView.setVisibility(View.VISIBLE);
                timeView.setText(context.getString(R.string.offline_text_field_update_time,
                        OfflineSmartTime.format(context,
                                ODateUtils.toUnixMillis(signature.getUpdateTime()))));
            } else {
                timeView.setVisibility(View.GONE);
            }
            previewView.setBackgroundResource(R.drawable.offline_signature_preview_bg);
            imageView.setVisibility(View.VISIBLE);
            placeholderView.setVisibility(View.GONE);
            bindSignatureImage(context, signatureItem, signature.getFileName(), imageView);
        } else {
            statusView.setText(R.string.offline_signature_state_pending);
            statusView.setTextColor(context.getColor(R.color.offline_form_muted));
            timeView.setVisibility(View.GONE);
            previewView.setBackgroundResource(R.drawable.offline_signature_pad_placeholder_bg);
            imageView.setVisibility(View.GONE);
            imageView.setImageDrawable(null);
            placeholderView.setVisibility(View.VISIBLE);
        }

        View.OnClickListener signClickListener = v -> {
            if (listener != null) {
                listener.onSignRequested(userName);
            }
        };
        // 操作入口就是右侧预览区：未签 → 去签名；已签 → 重新签名（二次确认在 ViewHolder 中处理）。
        previewView.setOnClickListener(signClickListener);
        previewView.setContentDescription(context.getString(
                R.string.offline_cd_signature_user_status,
                userName,
                statusView.getText(),
                context.getString(signed ? R.string.offline_signature_resign : R.string.offline_signature_go_sign)));
    }

    private static void bindSignatureImage(Context context, SignatureFormItem signatureItem,
                                           String fileName, ImageView imageView) {
        File signatureFile = Utils.resolveLocalFile(context, signatureItem.getPatternId(), fileName);
        if (signatureFile == null || !signatureFile.exists()) {
            imageView.setVisibility(View.GONE);
            return;
        }
        Glide.with(imageView)
                .load(Uri.fromFile(signatureFile))
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate()
                .into(imageView);
    }
}
