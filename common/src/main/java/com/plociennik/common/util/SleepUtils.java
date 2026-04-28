package com.plociennik.common.util;

import com.plociennik.common.errorhandling.exceptions.InkoRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SleepUtils {

    public static void sleep(long milliseconds) {
        if (milliseconds < 0) {
            throw new InkoRuntimeException("202504171538", "Sleep duration cannot be negative.");
        }
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread interrupted while sleeping: {}", e.getMessage());
        }
    }

    public static void sleepSeconds(long seconds) {
        sleep(seconds * 1000);
    }
}
