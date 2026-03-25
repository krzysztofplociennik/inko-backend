package com.plociennik.service.article.mapper;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.TagEntity;
import com.plociennik.service.article.dto.ArticleDetails;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ArticleReadMapper {

    public ArticleDetails mapToDetails(ArticleEntity articleEntity) {
        return ArticleDetails.builder()
                .id(articleEntity.getId().toString())
                .title(articleEntity.getTitle())
                .content(articleEntity.getContent())
                .type(articleEntity.getType().toString())
                .tags(mapTags(articleEntity))
                .creationDate(articleEntity.getCreationDate())
                .modificationDate(articleEntity.getModificationDate())
                .build();
    }

    private Set<String> mapTags(ArticleEntity articleEntity) {
        List<TagEntity> tags = articleEntity.getTags();
        return tags.stream()
                .map(TagEntity::getValue)
                .collect(Collectors.toSet());
    }
}
