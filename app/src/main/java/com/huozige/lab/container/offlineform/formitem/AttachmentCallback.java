package com.huozige.lab.container.offlineform.formitem;

import com.huozige.lab.container.offlineform.model.formitem.AttachmentFormItemValue;

import java.util.List;

public interface AttachmentCallback {
    void onAttachmentsSelected(List<AttachmentFormItemValue> attachments);
}
