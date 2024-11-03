package com.plociennik.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class ArticleUpdate implements Serializable {

    private String id;
    private String title;
    private String content;
    private Set<String> tags;
}
