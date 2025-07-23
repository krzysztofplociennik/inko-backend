package com.plociennik.web.api;

import com.plociennik.service.article.ArticleSearchService;
import com.plociennik.service.article.dto.SearchArticlesItem;
import com.plociennik.service.article.search.ArticleFilter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping(value = "/search-articles")
@AllArgsConstructor
public class SearchArticlesController {

    private ArticleSearchService service;

    @PostMapping
    public ResponseEntity<Page<SearchArticlesItem>> searchArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestBody
            ArticleFilter articleFilter) {
        Page<SearchArticlesItem> result = service.search(page, size, articleFilter);
        return ResponseEntity.ok(result);
    }
}
