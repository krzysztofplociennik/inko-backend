package com.plociennik.web.api;

import com.plociennik.service.search.autocomplete.AutocompleteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/autocomplete")
@CrossOrigin
@AllArgsConstructor
public class AutocompleteController {

    private final AutocompleteService service;

    @GetMapping
    public ResponseEntity<List<String>> getByPhrase(@RequestParam String searchPhrase) {
        List<String> autocompletes = service.findAllBySearchPhrase(searchPhrase);
        return ResponseEntity.ok(autocompletes);
    }
}
