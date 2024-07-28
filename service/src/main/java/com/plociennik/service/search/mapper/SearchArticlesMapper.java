package com.plociennik.service.search.mapper;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.TagEntity;
import com.plociennik.service.search.dto.SearchArticlesItem;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchArticlesMapper {

    public SearchArticlesItem mapToRead(ArticleEntity articleEntity) {

        SearchArticlesItem searchArticlesItem = new SearchArticlesItem(
                articleEntity.getId(),
                articleEntity.getTitle(),
                articleEntity.getType(),
                mapTags(articleEntity),
                articleEntity.getCreationDate()
        );

        return searchArticlesItem;
    }

    private Set<String> mapTags(ArticleEntity articleEntity) {

        List<TagEntity> tags = articleEntity.getTags();
        Set<String> collectedValues = tags.stream()
                .map(tag -> tag.getValue())
                .collect(Collectors.toSet());

        return collectedValues;
    }
}
