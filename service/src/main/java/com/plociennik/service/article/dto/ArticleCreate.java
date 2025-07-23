package com.plociennik.service.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class ArticleCreate implements Serializable {
    private String title;
    private String content;
    private String type;
    private Set<String> tags;
}
