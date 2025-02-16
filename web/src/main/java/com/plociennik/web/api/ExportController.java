package com.plociennik.web.api;

import com.plociennik.service.export.ExportService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/export")
@AllArgsConstructor
public class ExportController {

    private final ExportService exportService;

    @GetMapping(value = "/withHTML")
    public ResponseEntity<FileSystemResource> exportWithHTML() {
        File backupFile = exportService.exportArticles(true);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment");

        return ResponseEntity.ok()
                .headers(headers)
                .body(new FileSystemResource(backupFile));
    }

    @GetMapping(value = "/withoutHTML")
    public ResponseEntity<FileSystemResource> exportWithoutHTML() {
        File backupFile = exportService.exportArticles(false);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment");

        return ResponseEntity.ok()
                .headers(headers)
                .body(new FileSystemResource(backupFile));
    }
}
