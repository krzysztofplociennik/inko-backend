package com.plociennik.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private DateUtil() {}

    public static String getFriendlyCurrentDateTime() {
        DateTimeFormatter friendlyFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(friendlyFormatter);
    }

}
