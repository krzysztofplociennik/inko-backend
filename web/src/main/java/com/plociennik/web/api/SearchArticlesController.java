package com.plociennik.web.api;

import com.plociennik.service.search.SearchArticlesService;
import com.plociennik.service.search.dto.SearchArticlesItem;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/search-articles")
@CrossOrigin
@AllArgsConstructor
public class SearchArticlesController {

    private SearchArticlesService service;

    @GetMapping()
    public ResponseEntity<List<SearchArticlesItem>> search() {
        List<SearchArticlesItem> articles = service.search("Idea");
        return ResponseEntity.ok(articles);
    }
}
