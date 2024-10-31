package com.plociennik.web.api;

import com.plociennik.service.search.SearchArticlesService;
import com.plociennik.service.search.dto.SearchArticlesItem;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/search-articles")
@AllArgsConstructor
public class SearchArticlesController {

    private SearchArticlesService service;

    @GetMapping()
    public ResponseEntity<List<SearchArticlesItem>> search(@RequestParam String searchPhrase) {
            List<SearchArticlesItem> articles = service.search(searchPhrase);
        return ResponseEntity.ok(articles);
    }
}
