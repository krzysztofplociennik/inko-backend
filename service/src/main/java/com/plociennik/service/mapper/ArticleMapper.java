package com.plociennik.service.mapper;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.ArticleType;
import com.plociennik.service.dto.ArticleCreate;
import com.plociennik.service.dto.ArticleRead;

import java.time.LocalDateTime;

public class ArticleMapper {

    public ArticleRead mapToRead(ArticleEntity articleEntity) {
        return new ArticleRead(
                articleEntity.getId().toString(),
                articleEntity.getTitle(),
                articleEntity.getContent(),
                articleEntity.getType(),
                articleEntity.getTags(),
                articleEntity.getCreationDate(),
                articleEntity.getModificationDate()
        );
    }

    public ArticleEntity mapToEntity(ArticleCreate articleCreate) {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle(articleCreate.getTitle());
        articleEntity.setContent(articleCreate.getContent());

        ArticleType createType = ArticleType.getType(articleCreate.getType());
        articleEntity.setType(createType);
        articleEntity.setTags(articleCreate.getTags());

        LocalDateTime currentTime = LocalDateTime.now();
        articleEntity.setCreationDate(currentTime);
        articleEntity.setModificationDate(null);

        return articleEntity;
    }
}
