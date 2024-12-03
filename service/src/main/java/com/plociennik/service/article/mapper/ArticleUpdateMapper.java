package com.plociennik.service.article.mapper;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.TagEntity;
import com.plociennik.service.article.dto.ArticleUpdate;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleUpdateMapper {

    public ArticleEntity map(ArticleEntity currentArticle, ArticleUpdate updatedArticle, List<TagEntity> mergedTags) {
        LocalDateTime modificationDate = LocalDateTime.now();
        currentArticle.setTitle(updatedArticle.getTitle());
        currentArticle.setContent(updatedArticle.getContent());
        currentArticle.setModificationDate(modificationDate);
        currentArticle.setTags(mergedTags);
        return currentArticle;
    }
}
