package com.huozige.lab.container.offlineform.formitem.image;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.huozige.lab.container.R;

/**
 * 图片来源抽屉：拍照 / 从相册选择 / 取消。
 *
 * <p>仅在表单配置允许图片上传（allowImageUpload = true）时由 {@link ImageViewHolder} 弹出；
 * 不允许上传时点击「添加」直接唤起相机。</p>
 */
public final class ImageSourceSheet {

    public interface SourceListener {
        void onCameraSelected();

        void onGallerySelected();
    }

    private ImageSourceSheet() {
    }

    public static void show(Context context, SourceListener listener) {
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View content = LayoutInflater.from(context).inflate(R.layout.offline_image_source_sheet, null);
        content.findViewById(R.id.image_source_camera).setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) {
                listener.onCameraSelected();
            }
        });
        content.findViewById(R.id.image_source_gallery).setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) {
                listener.onGallerySelected();
            }
        });
        content.findViewById(R.id.image_source_cancel).setOnClickListener(v -> dialog.dismiss());
        dialog.setContentView(content);
        dialog.show();
    }
}
