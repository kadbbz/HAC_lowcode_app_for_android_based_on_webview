package com.huozige.lab.container.offlineform.formitem.signature;

import com.huozige.lab.container.offlineform.model.formitem.signature.SignatureFormItem;

public interface SignatureCaptureHost {
    void captureSignature(SignatureFormItem item, SignatureCallback callback);

    void onSignatureChanged(SignatureFormItem item);
}
