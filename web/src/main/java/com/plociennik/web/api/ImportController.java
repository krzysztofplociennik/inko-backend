package com.plociennik.web.api;

import com.plociennik.service.importing.ImportService;
import com.plociennik.service.importing.dto.ImportFilesRequestBody;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/import")
@AllArgsConstructor
public class ImportController {

    private final ImportService importService;
    
    @PostMapping(value = "/multiple", consumes = "multipart/form-data")
    public ResponseEntity<String> importFiles(@ModelAttribute ImportFilesRequestBody requestBody) {
        importService.importFiles(requestBody);
        return ResponseEntity.ok("Files uploaded successfully!");
    }
}

