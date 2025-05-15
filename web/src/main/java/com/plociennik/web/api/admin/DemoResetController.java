package com.plociennik.web.api.admin;

import com.plociennik.service.admin.DemoResetService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/demoReset")
@AllArgsConstructor
public class DemoResetController {

    private final DemoResetService service;

    @PostMapping(value = "/reset")
    public ResponseEntity<String> reset(@RequestParam String adminToken) {
        service.reset(adminToken);
        return ResponseEntity.ok("Reset done.");
    }
}
