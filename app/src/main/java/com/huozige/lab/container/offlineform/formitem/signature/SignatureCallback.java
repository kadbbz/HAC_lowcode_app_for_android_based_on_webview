package com.huozige.lab.container.offlineform.formitem.signature;

import com.huozige.lab.container.offlineform.model.formitem.common.AttachmentFormItemValue;

public interface SignatureCallback {
    void onSignatureCaptured(AttachmentFormItemValue signature);
}
