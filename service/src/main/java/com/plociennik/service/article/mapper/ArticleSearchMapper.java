package com.plociennik.service.article.mapper;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.TagEntity;
import com.plociennik.service.article.dto.SearchArticlesItem;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ArticleSearchMapper {

    public SearchArticlesItem mapToRead(ArticleEntity articleEntity) {
        return SearchArticlesItem.builder()
                .id(articleEntity.getId())
                .title(articleEntity.getTitle())
                .type(articleEntity.getType())
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
