package com.plociennik.common.util;

public class StringUtil {

    public static String newLine(int numberOfNewLines) {
        final String NEW_LINE = "\n";
        return NEW_LINE.repeat(Math.max(0, numberOfNewLines));
    }
}
