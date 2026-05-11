package com.plociennik.service.article.dto;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record ArticleDetails(
        String id,
        String title,
        String content,
        String type,
        Set<String> tags,
        LocalDateTime creationDate,
        LocalDateTime modificationDate
) implements Serializable {}
