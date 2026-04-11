package com.plociennik.service.article.search;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
public class ArticleFilter {
    private String searchPhrase;
    private String type;
    private List<String> tags;
    private LocalDate creationDateFrom;
    private LocalDate creationDateTo;
    private ArticleSort sort;
}
