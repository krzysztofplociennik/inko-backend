package com.plociennik.service.article.dto;

import com.plociennik.service.article.validation.ValidType;
import com.plociennik.service.article.validation.ValidContent;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.io.Serializable;
import java.util.Set;

@Builder
public record ArticleUpdate(
        String id,

        @NotBlank(message = "Title cannot be blank")
        String title,

        @ValidType
        String type,

        @ValidContent
        String content,
        Set<String> tags
) implements Serializable {}
