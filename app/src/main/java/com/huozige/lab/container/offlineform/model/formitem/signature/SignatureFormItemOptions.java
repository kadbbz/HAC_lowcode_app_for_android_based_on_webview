package com.huozige.lab.container.offlineform.model.formitem.signature;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * 签名字段配置。users 既支持 JSON 字符串数组，也兼容“张三、李四、王五”形式的字符串。
 */
@Getter
@Setter
public class SignatureFormItemOptions {
    private Object users;

    public List<String> resolveUsers() {
        Set<String> result = new LinkedHashSet<>();
        if (users instanceof Collection<?>) {
            for (Object user : (Collection<?>) users) {
                appendUser(result, user);
            }
        } else if (users != null) {
            for (String user : String.valueOf(users).split("[,，、;；\\n]")) {
                appendUser(result, user);
            }
        }
        return new ArrayList<>(result);
    }

    private static void appendUser(Set<String> result, Object rawUser) {
        if (rawUser instanceof Map<?, ?>) {
            Map<?, ?> userObject = (Map<?, ?>) rawUser;
            Object name = userObject.get("userName");
            if (name == null) {
                name = userObject.get("name");
            }
            if (name == null) {
                name = userObject.get("value");
            }
            rawUser = name;
        }
        String user = rawUser == null ? "" : String.valueOf(rawUser).trim();
        if (!user.isEmpty()) {
            result.add(user);
        }
    }
}
