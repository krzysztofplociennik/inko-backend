package com.plociennik.service.article.dto;

import com.plociennik.model.ArticleType;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
public record SearchArticlesItem(
    UUID id,
    String title,
    ArticleType type,
    Set<String> tags,
    LocalDateTime creationDate,
    LocalDateTime modificationDate
) implements Serializable {}
