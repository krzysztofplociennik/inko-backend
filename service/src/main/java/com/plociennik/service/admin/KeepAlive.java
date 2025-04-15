package com.plociennik.service.admin;

import com.plociennik.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@SuppressWarnings("unused")
public class KeepAlive {

    @Scheduled(fixedRate = 14 * 60 * 1_000)
    public void myTaskFixedRate() {
        String message = "Keep-alive successful -> " + DateUtil.getFriendlyCurrentDateTime();
        log.info(message);
    }
}
