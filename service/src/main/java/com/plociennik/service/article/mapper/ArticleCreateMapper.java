package com.plociennik.service.article.mapper;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.ArticleType;
import com.plociennik.model.TagEntity;
import com.plociennik.service.article.dto.ArticleCreate;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleCreateMapper {

    public ArticleEntity mapToEntity(ArticleCreate articleCreate, List<TagEntity> tagsValues) {
        ArticleType createType = ArticleType.getType(articleCreate.getType());
        LocalDateTime currentTime = LocalDateTime.now();

        ArticleEntity articleEntity = ArticleEntity.builder()
                .title(articleCreate.getTitle())
                .content(articleCreate.getContent())
                .type(createType)
                .tags(tagsValues)
                .creationDate(currentTime)
                .build();

        for (TagEntity tagsValue : tagsValues) {
            List<ArticleEntity> articles = tagsValue.getArticles();
            articles.add(articleEntity);
        }

        return articleEntity;
    }
}
