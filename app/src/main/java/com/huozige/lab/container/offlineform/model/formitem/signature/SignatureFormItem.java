package com.huozige.lab.container.offlineform.model.formitem.signature;

import com.alibaba.fastjson.JSON;
import com.huozige.lab.container.offlineform.model.formitem.common.AttachmentFormItemValue;
import com.huozige.lab.container.offlineform.model.formitem.common.BaseFormItem;
import com.huozige.lab.container.offlineform.util.ODateUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignatureFormItem extends BaseFormItem {
    private List<String> users = new ArrayList<>();
    private List<SignatureFormItemValue> signatures = new ArrayList<>();
    private String patternId = "";
    private String disclaimer = "";

    public SignatureFormItem(String itemType, String id, String title, String hint, boolean required) {
        super(itemType, id, title, hint, required);
    }

    public void setUsers(List<String> users) {
        Set<String> normalizedUsers = new LinkedHashSet<>();
        if (users != null) {
            for (String user : users) {
                String normalizedUser = normalizeUserName(user);
                if (!normalizedUser.isEmpty()) {
                    normalizedUsers.add(normalizedUser);
                }
            }
        }
        this.users = new ArrayList<>(normalizedUsers);
        normalizeSignatures();
    }

    @Override
    public String getValue() {
        return JSON.toJSONString(signatures == null ? new ArrayList<>() : signatures);
    }

    public void setValue(String value) {
        signatures = parseSignatures(value, users);
        normalizeSignatures();
    }

    @Override
    public boolean isEmpty() {
        return getSignedCount() == 0;
    }

    public SignatureFormItemValue getSignature(String userName) {
        return findSignature(signatures, userName);
    }

    public void setSignature(String userName, AttachmentFormItemValue attachment) {
        String normalizedUser = normalizeUserName(userName);
        if (normalizedUser.isEmpty()) {
            return;
        }
        removeSignature(normalizedUser);
        if (attachment == null || attachment.getFileName() == null || attachment.getFileName().isEmpty()) {
            return;
        }
        SignatureFormItemValue signature = new SignatureFormItemValue();
        signature.setUserName(normalizedUser);
        signature.setFileName(attachment.getFileName());
        signature.setUpdateTime(ODateUtils.now());
        signatures.add(signature);
        sortSignaturesByUserOrder();
    }

    public void removeSignature(String userName) {
        String normalizedUser = normalizeUserName(userName);
        if (signatures != null) {
            signatures.removeIf(signature -> signature != null
                    && normalizedUser.equals(normalizeUserName(signature.getUserName())));
        }
    }

    public int getSignedCount() {
        int count = 0;
        if (users != null) {
            for (String user : users) {
                if (getSignature(user) != null) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public boolean validate() {
        clearError();
        if (!isRequired()) {
            return true;
        }
        if (users == null || users.isEmpty()) {
            setErrorMessage("未配置签名人");
            return false;
        }
        for (String user : users) {
            if (getSignature(user) == null) {
                setErrorMessage("请完成" + user + "的签名");
                return false;
            }
        }
        return true;
    }

    @Override
    public void clearError() {
        setErrorMessage(null);
    }

    public static List<SignatureFormItemValue> parseSignatures(String value, List<String> users) {
        List<SignatureFormItemValue> result = new ArrayList<>();
        if (value == null || value.trim().isEmpty()) {
            return result;
        }
        try {
            List<SignatureFormItemValue> parsed = JSON.parseArray(value, SignatureFormItemValue.class);
            if (parsed == null) {
                return result;
            }
            int legacyUserIndex = 0;
            for (SignatureFormItemValue signature : parsed) {
                if (signature == null || signature.getFileName() == null || signature.getFileName().isEmpty()) {
                    continue;
                }
                String userName = normalizeUserName(signature.getUserName());
                if (userName.isEmpty() && users != null) {
                    while (legacyUserIndex < users.size() && containsUser(result, users.get(legacyUserIndex))) {
                        legacyUserIndex++;
                    }
                    if (legacyUserIndex < users.size()) {
                        userName = normalizeUserName(users.get(legacyUserIndex++));
                    }
                }
                if (!userName.isEmpty() && !containsUser(result, userName)) {
                    signature.setUserName(userName);
                    result.add(signature);
                } else if (users == null && userName.isEmpty()) {
                    // 保留旧版未记录签名人名称的附件，避免导出时丢失历史签名。
                    result.add(signature);
                }
            }
        } catch (RuntimeException ignored) {
            return new ArrayList<>();
        }
        return result;
    }

    public static List<AttachmentFormItemValue> parseAttachments(String value) {
        List<AttachmentFormItemValue> attachments = new ArrayList<>();
        if (value == null || value.trim().isEmpty()) {
            return attachments;
        }
        try {
            List<SignatureFormItemValue> parsed = JSON.parseArray(value, SignatureFormItemValue.class);
            if (parsed == null) {
                return attachments;
            }
            for (SignatureFormItemValue signature : parsed) {
                if (signature == null || signature.getFileName() == null || signature.getFileName().isEmpty()) {
                    continue;
                }
                AttachmentFormItemValue attachment = new AttachmentFormItemValue();
                String userName = normalizeUserName(signature.getUserName());
                attachment.setOriginalName(userName.isEmpty() ? "签名" : userName);
                attachment.setFileName(signature.getFileName());
                attachments.add(attachment);
            }
        } catch (RuntimeException ignored) {
            return new ArrayList<>();
        }
        return attachments;
    }

    private void normalizeSignatures() {
        if (signatures == null) {
            signatures = new ArrayList<>();
            return;
        }
        List<SignatureFormItemValue> normalized = new ArrayList<>();
        for (String user : users) {
            SignatureFormItemValue signature = findSignature(signatures, user);
            if (signature != null && signature.getFileName() != null && !signature.getFileName().isEmpty()) {
                signature.setUserName(user);
                normalized.add(signature);
            }
        }
        signatures = normalized;
    }

    private void sortSignaturesByUserOrder() {
        List<SignatureFormItemValue> sorted = new ArrayList<>();
        for (String user : users) {
            SignatureFormItemValue signature = findSignature(signatures, user);
            if (signature != null) {
                sorted.add(signature);
            }
        }
        signatures = sorted;
    }

    private static SignatureFormItemValue findSignature(List<SignatureFormItemValue> values, String userName) {
        if (values == null) {
            return null;
        }
        String normalizedUser = normalizeUserName(userName);
        for (SignatureFormItemValue signature : values) {
            if (signature != null
                    && signature.getFileName() != null && !signature.getFileName().isEmpty()
                    && normalizedUser.equals(normalizeUserName(signature.getUserName()))) {
                return signature;
            }
        }
        return null;
    }

    private static boolean containsUser(List<SignatureFormItemValue> values, String userName) {
        return findSignature(values, userName) != null;
    }

    private static String normalizeUserName(String userName) {
        return userName == null ? "" : userName.trim();
    }
}
