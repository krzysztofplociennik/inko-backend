package com.plociennik.service.mapper;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.ArticleType;
import com.plociennik.model.TagEntity;
import com.plociennik.service.dto.ArticleCreate;
import com.plociennik.service.dto.ArticleRead;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ArticleMapper {

    public ArticleRead mapToRead(ArticleEntity articleEntity) {
        return new ArticleRead(
                articleEntity.getId().toString(),
                articleEntity.getTitle(),
                articleEntity.getContent(),
                articleEntity.getType(),
                mapTags(articleEntity),
                articleEntity.getCreationDate(),
                articleEntity.getModificationDate()
        );
    }

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

    private Set<String> mapTags(ArticleEntity articleEntity) {

        List<TagEntity> tags = articleEntity.getTags();
        Set<String> collectedTagNames = tags.stream()
                .map(TagEntity::getValue)
                .collect(Collectors.toSet());

        return collectedTagNames;
    }
}
