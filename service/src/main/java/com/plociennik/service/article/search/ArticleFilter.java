package com.plociennik.service.article.search;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
public class ArticleFilter {
    private int page;
    private int size;
    private String searchPhrase;
    private String type;
    private List<String> tags;
    private Date creationDateFrom;
    private Date creationDateTo;
}
