package com.plociennik.service.article.mapper;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.TagEntity;
import com.plociennik.service.article.dto.AllArticlesItem;
import com.plociennik.service.article.dto.ArticleDetails;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ArticleReadMapper {

    public ArticleDetails mapToDetails(ArticleEntity articleEntity) {
        return new ArticleDetails(
                articleEntity.getId().toString(),
                articleEntity.getTitle(),
                articleEntity.getContent(),
                articleEntity.getType().toString(),
                mapTags(articleEntity),
                articleEntity.getCreationDate(),
                articleEntity.getModificationDate()
        );
    }

    public AllArticlesItem mapToAllItem(ArticleEntity articleEntity) {

        AllArticlesItem allArticlesItem = new AllArticlesItem(
                articleEntity.getId(),
                articleEntity.getTitle(),
                articleEntity.getType(),
                mapTags(articleEntity),
                articleEntity.getCreationDate(),
                articleEntity.getModificationDate()
        );

        return allArticlesItem;
    }

    private Set<String> mapTags(ArticleEntity articleEntity) {

        List<TagEntity> tags = articleEntity.getTags();
        Set<String> collectedTagNames = tags.stream()
                .map(TagEntity::getValue)
                .collect(Collectors.toSet());

        return collectedTagNames;
    }
}
