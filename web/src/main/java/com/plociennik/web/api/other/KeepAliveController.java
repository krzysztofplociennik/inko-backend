package com.plociennik.web.api.other;

import com.plociennik.common.util.DateUtil;
import com.plociennik.service.other.KeepAliveService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/keep-alive")
@AllArgsConstructor
@Slf4j
public class KeepAliveController {

    private final KeepAliveService keepAliveService;

    @GetMapping
    public ResponseEntity<String> ping() {
        String message = "Keep-alive successful -> " + DateUtil.getFriendlyCurrentDateTime();
        this.keepAliveService.findFirstArticle();
        log.info(message);
        return ResponseEntity.ok(message);
    }
}
