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
import com.huozige.lab.container.offlineform.util.Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 签名单人卡片与进度条的公共渲染逻辑：表单卡片（单人）与人员签名抽屉（多人）共用同一套样式。
 */
final class SignatureUi {

    interface OnSignRequested {
        void onSignRequested(String userName);
    }

    private static final String TIME_PATTERN = "yyyy-MM-dd HH:mm";

    private SignatureUi() {
    }

    /** 进度条：按已签 / 总数分配权重，填充色为成功绿。 */
    static void renderProgress(LinearLayout progressLayout, int signed, int total) {
        if (progressLayout == null) {
            return;
        }
        progressLayout.removeAllViews();
        if (total <= 0) {
            progressLayout.setVisibility(View.GONE);
            return;
        }
        int safeSigned = Math.max(0, Math.min(signed, total));
        View fill = new View(progressLayout.getContext());
        fill.setBackgroundResource(R.drawable.offline_signature_progress_fill);
        progressLayout.addView(fill, new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT, safeSigned));

        View rest = new View(progressLayout.getContext());
        progressLayout.addView(rest, new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT, total - safeSigned));
        progressLayout.setVisibility(View.VISIBLE);
    }

    static View createUserCard(Context context, SignatureFormItem signatureItem, String userName,
                               OnSignRequested listener) {
        View card = LayoutInflater.from(context)
                .inflate(R.layout.custom_form_item_signature_user_card, null, false);
        int gap = Math.round(10 * context.getResources().getDisplayMetrics().density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = gap;
        card.setLayoutParams(params);
        bindUserCard(card, context, signatureItem, userName, listener);
        return card;
    }

    static void bindUserCard(View card, Context context, SignatureFormItem signatureItem, String userName,
                             OnSignRequested listener) {
        ImageView stateView = card.findViewById(R.id.signature_user_state);
        TextView nameView = card.findViewById(R.id.signature_user_name);
        TextView statusView = card.findViewById(R.id.signature_user_status);
        TextView timeView = card.findViewById(R.id.signature_user_time);
        FrameLayout previewView = card.findViewById(R.id.signature_user_preview);
        ImageView imageView = card.findViewById(R.id.signature_user_image);
        LinearLayout placeholderView = card.findViewById(R.id.signature_user_placeholder);
        TextView actionView = card.findViewById(R.id.signature_user_action);

        SignatureFormItemValue signature = signatureItem.getSignature(userName);
        boolean signed = signature != null;
        nameView.setText(userName);

        if (signed) {
            stateView.setImageResource(R.drawable.ic_offline_signed_badge);
            statusView.setText(R.string.offline_text_signature_set);
            statusView.setTextColor(context.getColor(R.color.offline_form_ok));
            if (signature.getUpdateTime() != null) {
                timeView.setVisibility(View.VISIBLE);
                timeView.setText(formatTime(signature.getUpdateTime()));
            } else {
                timeView.setVisibility(View.GONE);
            }
            previewView.setBackgroundResource(R.drawable.offline_signature_preview_bg);
            imageView.setVisibility(View.VISIBLE);
            placeholderView.setVisibility(View.GONE);
            bindSignatureImage(context, signatureItem, signature.getFileName(), imageView);
            actionView.setBackgroundResource(R.drawable.offline_signature_button_secondary_bg);
            actionView.setTextColor(context.getColor(R.color.offline_form_ink));
            actionView.setText(R.string.offline_signature_resign);
        } else {
            stateView.setImageResource(R.drawable.ic_offline_pending_badge);
            statusView.setText(R.string.offline_signature_state_pending);
            statusView.setTextColor(context.getColor(R.color.offline_form_muted));
            timeView.setVisibility(View.GONE);
            previewView.setBackgroundResource(R.drawable.offline_signature_pad_placeholder_bg);
            imageView.setVisibility(View.GONE);
            imageView.setImageDrawable(null);
            placeholderView.setVisibility(View.VISIBLE);
            actionView.setBackgroundResource(R.drawable.offline_signature_button_primary_bg);
            actionView.setTextColor(context.getColor(android.R.color.white));
            actionView.setText(R.string.offline_signature_go_sign);
        }

        View.OnClickListener signClickListener = v -> {
            if (listener != null) {
                listener.onSignRequested(userName);
            }
        };
        previewView.setOnClickListener(signClickListener);
        actionView.setOnClickListener(signClickListener);
        card.setContentDescription(context.getString(
                R.string.offline_cd_signature_user_status, userName, statusView.getText()));
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

    static String formatTime(Double oDate) {
        if (oDate == null) {
            return "";
        }
        return new SimpleDateFormat(TIME_PATTERN, Locale.CHINA)
                .format(new Date(ODateUtils.toUnixMillis(oDate)));
    }
}
