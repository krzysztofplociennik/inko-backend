package com.plociennik.service.article.dto;

import com.plociennik.model.ArticleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class AllArticlesItem implements Serializable {
    private UUID id;
    private String title;
    private ArticleType type;
    private Set<String> tags;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
}
