package com.huozige.lab.container.utilities;

public final class StringUtils {
    private StringUtils() {
    }

    public static boolean isNullOrBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isNotBlank(String value) {
        return !isNullOrBlank(value);
    }
}
