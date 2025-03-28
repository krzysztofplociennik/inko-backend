package com.plociennik.web.api;

import com.plociennik.service.importing.ImportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/import")
@AllArgsConstructor
public class ImportController {

    private final ImportService importService;

    @GetMapping(value = "/multiple")
    public ResponseEntity<String> importFiles() {
        String response = importService.importFiles();
        return ResponseEntity.ok(response);
    }
}
