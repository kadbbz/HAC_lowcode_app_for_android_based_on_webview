package com.huozige.lab.container.offlineform.formitem;

import com.huozige.lab.container.offlineform.model.formitem.common.AttachmentFormItemValue;

import java.util.List;

public interface AttachmentCallback {
    void onAttachmentsSelected(List<AttachmentFormItemValue> attachments);

    /** 拍照/相册已返回、后台处理（水印 / 压缩）开始时触发，用于展示「处理中」占位。 */
    default void onAttachmentsProcessingStarted() {
    }

    /** 用户取消，或后台处理失败时触发，用于移除「处理中」占位。 */
    default void onAttachmentsProcessingCancelled() {
    }
}
