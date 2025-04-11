package com.plociennik.web.api;

import com.plociennik.service.importing.ImportService;
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

    @PostMapping(value = "/single", consumes = "multipart/form-data")
    public ResponseEntity<String> importFiles(@ModelAttribute MultipartFile file) {
        importService.importFiles(file);
        return ResponseEntity.ok("The file uploaded successfully!");
    }
}

