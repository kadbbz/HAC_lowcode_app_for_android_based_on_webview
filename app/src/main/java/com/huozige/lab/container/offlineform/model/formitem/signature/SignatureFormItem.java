package com.huozige.lab.container.offlineform.model.formitem.signature;

import com.huozige.lab.container.offlineform.model.formitem.common.AttachmentFormItemValue;
import com.huozige.lab.container.offlineform.model.formitem.image.ImageFormItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 单个签名图片表单项。
 *
 * <p>签名最终仍以离线附件值保存，这样历史记录、导出和附件清理可以复用图片字段的处理方式，
 * 但在编辑界面中只允许维护一张签名图片。</p>
 */
public class SignatureFormItem extends ImageFormItem {
    public static final int MAX_COUNT = 1;

    public SignatureFormItem(String itemType, String id, String title, String hint, boolean required) {
        super(itemType, id, title, hint, required);
        setMaxCount(MAX_COUNT);
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        keepOnlyFirstSignature();
    }

    public AttachmentFormItemValue getSignature() {
        List<AttachmentFormItemValue> signatures = getImages();
        return signatures == null || signatures.isEmpty() ? null : signatures.get(0);
    }

    public void setSignature(AttachmentFormItemValue signature) {
        List<AttachmentFormItemValue> signatures = new ArrayList<>();
        if (signature != null && signature.getFileName() != null && !signature.getFileName().isEmpty()) {
            signatures.add(signature);
        }
        setImages(signatures);
    }

    public void clearSignature() {
        setImages(new ArrayList<>());
    }

    @Override
    public boolean validate() {
        clearError();
        if (isRequired() && isEmpty()) {
            setErrorMessage("请签署" + getTitle());
            return false;
        }
        return true;
    }

    private void keepOnlyFirstSignature() {
        List<AttachmentFormItemValue> signatures = getImages();
        if (signatures != null && signatures.size() > MAX_COUNT) {
            setImages(new ArrayList<>(signatures.subList(0, MAX_COUNT)));
        }
    }
}
