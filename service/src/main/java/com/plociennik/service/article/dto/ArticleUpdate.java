package com.plociennik.service.article.dto;

import com.plociennik.service.article.validation.ValidType;
import com.plociennik.service.article.validation.ValidContent;
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

    @ValidType
    private String type;

    @ValidContent
    private String content;

    private Set<String> tags;
}
