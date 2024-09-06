package com.project.server.utils;

import java.security.SecureRandom;

public class StringUtil {

    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomAlphanumeric(int length){
        SecureRandom random = new SecureRandom();

        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }

        StringBuilder result = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALPHANUMERIC.length());
            result.append(ALPHANUMERIC.charAt(randomIndex));
        }

        return result.toString();
    }
}
