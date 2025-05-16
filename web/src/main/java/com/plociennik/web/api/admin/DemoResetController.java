package com.plociennik.web.api.admin;

import com.plociennik.service.admin.DemoResetService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/demoReset")
@AllArgsConstructor
public class DemoResetController {

    private final DemoResetService service;

    @PostMapping(value = "/reset/{adminToken}")
    public ResponseEntity<String> reset(@PathVariable String adminToken) {
        service.reset(adminToken);
        return ResponseEntity.ok("Reset done.");
    }
}
