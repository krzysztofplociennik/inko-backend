package com.plociennik.web.api;

import com.plociennik.service.article.ArticleSearchService;
import com.plociennik.service.article.dto.SearchArticlesItem;
import com.plociennik.service.article.search.ArticleFilter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping(value = "/search-articles")
@AllArgsConstructor
public class SearchArticlesController {

    private ArticleSearchService service;

    @GetMapping
    public ResponseEntity<List<SearchArticlesItem>> search(@RequestParam String searchPhrase) {
            List<SearchArticlesItem> articles = service.search(searchPhrase);
        return ResponseEntity.ok(articles);
    }

    @PostMapping("/with-filter")
    public ResponseEntity<Page<SearchArticlesItem>> searchArticles(
            @RequestBody
            ArticleFilter articleFilter) {
        Page<SearchArticlesItem> page = service.search(articleFilter);
        return ResponseEntity.ok(page);
    }
}
