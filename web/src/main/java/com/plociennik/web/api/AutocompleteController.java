package com.plociennik.web.api;

import com.plociennik.service.autocomplete.AutocompleteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/autocomplete")
@AllArgsConstructor
public class AutocompleteController {

    private final AutocompleteService service;

    @Operation(
            summary = "Fetch all possible autocompletes given a phrase"
    )
    @GetMapping
    public ResponseEntity<List<String>> getByPhrase(@RequestParam String searchPhrase) {
        List<String> autocompletes = service.findAllBySearchPhrase(searchPhrase);
        return ResponseEntity.ok(autocompletes);
    }
}
