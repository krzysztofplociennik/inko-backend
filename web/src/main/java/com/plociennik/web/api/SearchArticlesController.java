package com.plociennik.web.api;

import com.plociennik.service.article.ArticleSearchService;
import com.plociennik.service.article.dto.SearchArticlesItem;
import com.plociennik.service.article.search.ArticleFilter;
import com.plociennik.service.article.search.ArticleSort;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/search-articles")
@AllArgsConstructor
public class SearchArticlesController {

    private ArticleSearchService service;

    @GetMapping
    public ResponseEntity<Page<SearchArticlesItem>> searchArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchPhrase,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate creationDateFrom,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate creationDateTo,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortType) {

        ArticleSort sort = new ArticleSort(sortField, sortType);
        ArticleFilter filter = ArticleFilter.builder()
                .searchPhrase(searchPhrase)
                .type(type)
                .tags(tags)
                .creationDateFrom(creationDateFrom)
                .creationDateTo(creationDateTo)
                .sort(sort)
                .build();

        Page<SearchArticlesItem> result = service.search(page, size, filter);
        return ResponseEntity.ok(result);
    }}
