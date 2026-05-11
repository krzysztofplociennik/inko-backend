package com.plociennik.service.article.dto;

import com.plociennik.service.article.validation.ValidContent;
import com.plociennik.service.article.validation.ValidType;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.io.Serializable;
import java.util.Set;

@Builder
public record ArticleCreate(
        @NotBlank(message = "Title cannot be blank")
        String title,

        @ValidContent
        String content,

        @NotBlank(message = "Type cannot be blank")
        @ValidType
        String type,

        Set<String> tags
) implements Serializable {}
