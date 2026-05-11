package com.plociennik.service.article.mapper;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.ArticleType;
import com.plociennik.model.TagEntity;
import com.plociennik.service.article.dto.ArticleCreate;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleCreateMapper {

    public ArticleEntity mapToEntity(ArticleCreate articleCreate) {
        ArticleType createType = ArticleType.getType(articleCreate.type());
        LocalDateTime currentTime = LocalDateTime.now();
        List<TagEntity> tags = mapTags(articleCreate);

        ArticleEntity articleEntity = ArticleEntity.builder()
                .title(articleCreate.title())
                .content(articleCreate.content())
                .type(createType)
                .tags(tags)
                .creationDate(currentTime)
                .build();

        for (TagEntity tag : tags) {
            List<ArticleEntity> articles = tag.getArticles();
            articles.add(articleEntity);
        }

        return articleEntity;
    }

    private List<TagEntity> mapTags(ArticleCreate articleCreate) {
        return articleCreate.tags().stream()
                .map(t -> TagEntity.builder().value(t).build())
                .toList();
    }
}
