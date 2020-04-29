package com.kstarrain.provider.utils;

import java.util.UUID;

public final class KeyGenUtils {
    private KeyGenUtils() {
    }

    public static String newUuid(String separator) {
        return UUID.randomUUID().toString().replace("-", separator);
    }

    public static String newUuid() {
        return newUuid("");
    }
}