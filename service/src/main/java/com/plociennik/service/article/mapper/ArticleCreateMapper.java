package com.plociennik.service.article.mapper;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.ArticleType;
import com.plociennik.model.TagEntity;
import com.plociennik.service.article.dto.ArticleCreate;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleCreateMapper {

    public ArticleEntity mapToEntity(ArticleCreate articleCreate, List<TagEntity> tagsValues) {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle(articleCreate.getTitle());
        articleEntity.setContent(articleCreate.getContent());

        ArticleType createType = ArticleType.getType(articleCreate.getType());
        articleEntity.setType(createType);
        articleEntity.setTags(tagsValues);

        LocalDateTime currentTime = LocalDateTime.now();
        articleEntity.setCreationDate(currentTime);
        articleEntity.setModificationDate(null);

        for (TagEntity tagsValue : tagsValues) {
            List<ArticleEntity> articles = tagsValue.getArticles();
            articles.add(articleEntity);
        }

        return articleEntity;
    }
}
