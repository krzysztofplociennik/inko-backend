package com.plociennik.web.api;

import com.plociennik.service.export.ExportService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "Export all existing articles with HTML tags",
            description = "Fetches all articles with non processed HTML tags in their contents and then packages them" +
                    " into a single ZIP file."
    )
    @GetMapping(value = "/withHTML")
    public ResponseEntity<FileSystemResource> exportWithHTML() {
        File backupFile = exportService.packageAllArticlesIntoZip(true);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment");

        return ResponseEntity.ok()
                .headers(headers)
                .body(new FileSystemResource(backupFile));
    }

    @Operation(
            summary = "Export all existing articles with no HTML tags",
            description = "Fetches all articles with deleted HTML tags in their contents and then packages them" +
                    " into a single ZIP file."
    )
    @GetMapping(value = "/withoutHTML")
    public ResponseEntity<FileSystemResource> exportWithoutHTML() {
        File backupFile = exportService.packageAllArticlesIntoZip(false);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment");

        return ResponseEntity.ok()
                .headers(headers)
                .body(new FileSystemResource(backupFile));
    }
}
