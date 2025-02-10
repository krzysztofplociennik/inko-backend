package com.plociennik.web.api;

import com.plociennik.service.backup.BackupService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/backup")
@AllArgsConstructor
public class BackupController {

    private final BackupService backupService;

    @GetMapping
    public ResponseEntity<String> doBackup() {
        this.backupService.doBackup();
        return ResponseEntity.ok("is good");
    }
}
