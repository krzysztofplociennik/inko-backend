package com.plociennik.service.dto;

import com.plociennik.model.ArticleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class ArticleDetails implements Serializable {

    private String id;
    private String title;
    private String content;
    private String type;
    private Set<String> tags;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

}
