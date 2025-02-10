package com.plociennik.web.api;

import com.plociennik.service.backup.BackupService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/backup")
@AllArgsConstructor
public class BackupController {

    private final BackupService backupService;

    @GetMapping
    public ResponseEntity<FileSystemResource> downloadBackup() {
        File backupFile = backupService.doBackup();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment");

        return ResponseEntity.ok()
                .headers(headers)
                .body(new FileSystemResource(backupFile));
    }
}
