package com.plociennik.web.api;

import com.plociennik.service.importing.ImportService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/import")
@AllArgsConstructor
@SuppressWarnings("unused")
public class ImportController {

    private final ImportService importService;

    @Operation(
            summary = "Import a file as a new article",
            description = "Imports a single file that, after successful validation, is mapped into an article" +
                    " and saved to the database."
    )
    @PostMapping(value = "/single", consumes = "multipart/form-data")
    public ResponseEntity<String> importFile(@ModelAttribute MultipartFile file) {
        importService.importFiles(file);
        return ResponseEntity.ok("The file uploaded successfully!");
    }
}

