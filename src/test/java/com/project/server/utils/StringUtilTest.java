package com.project.server.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilTest {

    @Test
    void testGenerateRandomAlphanumeric_success() {
        String result = StringUtil.generateRandomAlphanumeric(10);

        Assertions.assertEquals(10, result.length());
        Assertions.assertTrue(result.matches("[a-zA-Z0-9]+"));
    }

    @Test
    void testGenerateRandomAlphanumeric_fail() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> StringUtil.generateRandomAlphanumeric(0));

        Assertions.assertEquals("Length must be greater than 0", thrown.getMessage());
    }
}
