package com.dev.smart.backend.common.util;

import java.util.Random;

public class Base62Encoder {

    private static final String BASE62 =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static final Random random = new Random();

    public static String generateShortCode(int length) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(BASE62.charAt(random.nextInt(BASE62.length())));
        }

        return sb.toString();
    }
}
