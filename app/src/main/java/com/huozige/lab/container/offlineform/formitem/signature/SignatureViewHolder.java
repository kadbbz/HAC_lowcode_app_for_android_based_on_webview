package com.huozige.lab.container.offlineform.formitem.signature;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.model.formitem.signature.SignatureFormItem;
import com.huozige.lab.container.proxy.support.offlinecustomform.viewholder.BaseViewHolder;

import java.util.List;

import static com.huozige.lab.container.offlineform.util.OfflineFormUiUnitHelper.dp;

/**
 * 签名列表项：整块卡片包裹。
 *
 * <ul>
 *     <li>单签名人：直接展示该人员的签名卡片（状态、签名图、去签名 / 重新签名）；</li>
 *     <li>多签名人：标题右侧显示已签 / 待签数量，卡片内展示摘要与签名进度条，
 *     点击卡片从底部弹出「人员签名」抽屉，逐人签名。</li>
 * </ul>
 */
public class SignatureViewHolder extends BaseViewHolder {
    private final TextView tvTitle;
    private final TextView tvRequired;
    private final TextView tvCount;
    private final LinearLayout summaryLayout;
    private final TextView summaryTitle;
    private final TextView summarySubtitle;
    private final LinearLayout progressLayout;
    private final LinearLayout singleContainer;
    private final View cardContainer;
    private final TextView tvError;

    private SignatureFormItem signatureItem;
    private SignatureSheet signatureSheet;

    public SignatureViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvRequired = itemView.findViewById(R.id.tv_required);
        tvCount = itemView.findViewById(R.id.tv_signature_count);
        summaryLayout = itemView.findViewById(R.id.signature_summary);
        summaryTitle = itemView.findViewById(R.id.signature_summary_title);
        summarySubtitle = itemView.findViewById(R.id.signature_summary_subtitle);
        progressLayout = itemView.findViewById(R.id.layout_signature_progress);
        singleContainer = itemView.findViewById(R.id.signature_single_container);
        cardContainer = itemView.findViewById(R.id.signature_card);
        tvError = itemView.findViewById(R.id.tv_error);
        itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(@NonNull View v) {
            }

            @Override
            public void onViewDetachedFromWindow(@NonNull View v) {
                dismissSheet();
            }
        });
    }

    @Override
    public void bind(BaseFormItem item) {
        bind(item, getBindingAdapterPosition());
    }

    @Override
    public void bind(BaseFormItem item, int position) {
        signatureItem = (SignatureFormItem) item;
        tvTitle.setText(signatureItem.getTitle());
        tvRequired.setVisibility(signatureItem.isRequired() ? View.VISIBLE : View.GONE);
        render();
        updateErrorState();
    }

    @Override
    public void updateErrorState() {
        if (signatureItem != null && signatureItem.getErrorMessage() != null) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(signatureItem.getErrorMessage());
        } else {
            tvError.setVisibility(View.GONE);
        }
    }

    private void render() {
        singleContainer.removeAllViews();
        cardContainer.setOnClickListener(null);
        if (signatureItem == null || signatureItem.getUsers() == null || signatureItem.getUsers().isEmpty()) {
            tvCount.setVisibility(View.GONE);
            summaryLayout.setVisibility(View.GONE);
            progressLayout.setVisibility(View.GONE);
            singleContainer.setVisibility(View.VISIBLE);
            singleContainer.addView(createEmptyView());
            return;
        }

        List<String> users = signatureItem.getUsers();
        int total = users.size();
        int signed = signatureItem.getSignedCount();

        if (total == 1) {
            // 单签名人：不展示摘要与进度，直接把该人员的签名卡片嵌在表单卡片里。
            tvCount.setVisibility(View.GONE);
            summaryLayout.setVisibility(View.GONE);
            progressLayout.setVisibility(View.GONE);
            singleContainer.setVisibility(View.VISIBLE);
            View userCard = SignatureUi.createUserCard(
                    itemView.getContext(), signatureItem, users.get(0), this::requestSign);
            // 去掉卡片自身背景与内边距，让内容融入外层表单卡片，避免卡中卡。
            if (userCard.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                ((LinearLayout.LayoutParams) userCard.getLayoutParams()).topMargin = 0;
            }
            userCard.setBackground(null);
            userCard.setPadding(0, dp(itemView.getContext(), 10), 0, 0);
            singleContainer.addView(userCard);
            return;
        }

        int pending = Math.max(0, total - signed);
        singleContainer.setVisibility(View.GONE);
        tvCount.setVisibility(View.VISIBLE);
        tvCount.setText(itemView.getContext()
                .getString(R.string.offline_signature_header_count, signed, pending));

        summaryLayout.setVisibility(View.VISIBLE);
        summaryTitle.setText(itemView.getContext()
                .getString(R.string.offline_signature_field_summary, signed, total));
        summarySubtitle.setText(pending > 0
                ? itemView.getContext().getString(R.string.offline_signature_field_subtitle_pending, pending)
                : itemView.getContext().getString(R.string.offline_signature_field_subtitle_done));
        summaryLayout.setOnClickListener(v -> showSheet());
        // 整张签名卡片都可点击，符合「点击签名卡片弹出抽屉」的预期。
        cardContainer.setOnClickListener(v -> showSheet());

        SignatureUi.renderProgress(progressLayout, signed, total);
    }

    private View createEmptyView() {
        TextView emptyView = new TextView(itemView.getContext());
        emptyView.setText(R.string.offline_text_no_signature_users);
        emptyView.setTextColor(itemView.getContext().getColor(R.color.offline_form_muted));
        emptyView.setTextSize(12f);
        emptyView.setGravity(android.view.Gravity.CENTER);
        emptyView.setPadding(0, dp(itemView.getContext(), 16), 0, dp(itemView.getContext(), 16));
        return emptyView;
    }

    private void showSheet() {
        if (signatureItem == null || signatureItem.getUsers() == null || signatureItem.getUsers().size() < 2) {
            return;
        }
        signatureSheet = SignatureSheet.show(
                itemView.getContext(), signatureItem, this::requestSign);
    }

    private void dismissSheet() {
        if (signatureSheet != null) {
            signatureSheet.dismiss();
            signatureSheet = null;
        }
    }

    /** 签名 / 重签入口：已签人员先二次确认，未签人员直接进入签名页。 */
    private void requestSign(String userName) {
        if (signatureItem == null || userName == null) {
            return;
        }
        if (signatureItem.getSignature(userName) != null) {
            confirmResign(userName);
        } else {
            captureSignature(userName);
        }
    }

    private void confirmResign(String userName) {
        new AlertDialog.Builder(itemView.getContext())
                .setTitle(R.string.offline_signature_resign_title)
                .setMessage(itemView.getContext().getString(R.string.offline_signature_resign_message, userName))
                .setPositiveButton(R.string.offline_signature_resign, (dialog, which) -> captureSignature(userName))
                .setNegativeButton(R.string.offline_button_cancel, null)
                .show();
    }

    private void captureSignature(String userName) {
        if (!(itemView.getContext() instanceof SignatureCaptureHost)) {
            Toast.makeText(itemView.getContext(), R.string.offline_toast_signature_not_supported, Toast.LENGTH_SHORT).show();
            return;
        }
        ((SignatureCaptureHost) itemView.getContext()).captureSignature(signatureItem, userName, signature -> {
            if (signature == null) {
                return;
            }
            signatureItem.setSignature(userName, signature);
            signatureItem.clearError();
            render();
            updateErrorState();
            if (signatureSheet != null) {
                signatureSheet.refresh();
            }
            notifySignatureChanged();
        });
    }

    private void notifySignatureChanged() {
        notifyFieldChanged();
        if (itemView.getContext() instanceof SignatureCaptureHost) {
            ((SignatureCaptureHost) itemView.getContext()).onSignatureChanged(signatureItem);
        }
    }
}
