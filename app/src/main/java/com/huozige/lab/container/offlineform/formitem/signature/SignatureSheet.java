package com.huozige.lab.container.offlineform.formitem.signature;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.huozige.lab.container.R;
import com.huozige.lab.container.offlineform.model.formitem.signature.SignatureFormItem;
import com.huozige.lab.container.offlineform.util.OfflineFormSheetHeight;

import java.util.List;

/**
 * 「人员签名」底部抽屉：标题行（左标题 / 右 n/m）+ 通栏签名人列表。
 *
 * <p>列表与表单字段同样式：直接通栏、行与行之间用 1dp 分隔线分隔，不再显示进度条。</p>
 * <p>签名完成回来后调用 {@link #refresh()} 原地刷新，不关闭抽屉。</p>
 */
public final class SignatureSheet {

    private final BottomSheetDialog dialog;
    private final View content;
    private final SignatureFormItem signatureItem;
    private final SignatureUi.OnSignRequested listener;
    private final TextView titleView;
    private final TextView countView;
    private final LinearLayout listLayout;
    private final View scrollView;

    private SignatureSheet(Context context, SignatureFormItem signatureItem, SignatureUi.OnSignRequested listener) {
        this.signatureItem = signatureItem;
        this.listener = listener;
        dialog = new BottomSheetDialog(context);
        content = LayoutInflater.from(context).inflate(R.layout.offline_signature_sheet, null);
        titleView = content.findViewById(R.id.signature_sheet_title);
        countView = content.findViewById(R.id.signature_sheet_count);
        listLayout = content.findViewById(R.id.signature_sheet_list);
        scrollView = content.findViewById(R.id.signature_sheet_scroll);
    }

    public static SignatureSheet show(Context context, SignatureFormItem signatureItem,
                                      SignatureUi.OnSignRequested listener) {
        SignatureSheet sheet = new SignatureSheet(context, signatureItem, listener);
        sheet.render();
        OfflineFormSheetHeight.showWithMeasuredHeight(context, sheet.dialog, sheet.content, sheet.scrollView);
        return sheet;
    }

    /** 已签状态变化后原地刷新标题计数与人员列表。 */
    public void refresh() {
        render();
    }

    public void dismiss() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /** 抽屉是否仍在显示：被用户下滑 / 点遮罩关闭后再次点击签名组件应重新弹出。 */
    public boolean isShowing() {
        return dialog.isShowing();
    }

    private void render() {
        titleView.setText(signatureItem.getTitle());
        List<String> users = signatureItem.getUsers();
        int total = users == null ? 0 : users.size();
        int signed = Math.max(0, Math.min(signatureItem.getSignedCount(), total));
        countView.setText(countView.getContext()
                .getString(R.string.offline_signature_sheet_ratio, signed, total));

        listLayout.removeAllViews();
        if (users == null) {
            return;
        }
        for (int i = 0; i < users.size(); i++) {
            if (i > 0) {
                listLayout.addView(createDivider());
            }
            listLayout.addView(SignatureUi.createUserCard(
                    listLayout.getContext(), signatureItem, users.get(i), listener));
        }
    }

    /** 与表单字段列表一致的通栏分隔线。 */
    private View createDivider() {
        View divider = new View(listLayout.getContext());
        divider.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                Math.max(1, Math.round(listLayout.getResources().getDisplayMetrics().density))));
        divider.setBackgroundResource(R.drawable.offline_form_row_divider);
        return divider;
    }
}
