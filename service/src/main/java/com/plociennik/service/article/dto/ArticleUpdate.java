package com.plociennik.service.article.dto;

import com.plociennik.service.article.validation.ValidArticleType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ArticleUpdate implements Serializable {
    private String id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Type cannot be blank")
    @ValidArticleType
    private String type;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    private Set<String> tags;
}
