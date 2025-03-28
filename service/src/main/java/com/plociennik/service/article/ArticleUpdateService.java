package com.plociennik.service.article;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.TagEntity;
import com.plociennik.model.repository.article.ArticleCustomRepository;
import com.plociennik.model.repository.article.ArticleRepository;
import com.plociennik.service.article.dto.ArticleDetails;
import com.plociennik.service.article.dto.ArticleUpdate;
import com.plociennik.service.article.mapper.ArticleReadMapper;
import com.plociennik.service.article.mapper.ArticleUpdateMapper;
import com.plociennik.service.tag.TagHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ArticleUpdateService {

    private final ArticleRepository articleRepository;
    private final ArticleCustomRepository articleCustomRepository;
    private final ArticleReadMapper articleReadMapper;
    private final ArticleUpdateMapper articleUpdateMapper;
    private final TagHelper tagHelper;

    public ArticleUpdateService(
            ArticleRepository articleRepository,
            ArticleCustomRepository articleCustomRepository,
            TagHelper tagHelper
    ) {
        this.articleRepository = articleRepository;
        this.articleCustomRepository = articleCustomRepository;
        this.tagHelper = tagHelper;
        this.articleReadMapper = new ArticleReadMapper();
        this.articleUpdateMapper = new ArticleUpdateMapper();
    }

    public ArticleDetails update(ArticleUpdate articleUpdate) {
        UUID uuid = UUID.fromString(articleUpdate.getId());
        ArticleEntity entity = articleCustomRepository.findByUUID(uuid);
        List<TagEntity> mergedTags = tagHelper.mergeExistingTagsWithNewTags(articleUpdate.getTags());
        ArticleEntity mappedArticle = articleUpdateMapper.map(entity, articleUpdate, mergedTags);
        ArticleEntity updatedArticle = articleRepository.save(mappedArticle);
        ArticleDetails response = articleReadMapper.mapToDetails(updatedArticle);
        return response;
    }
}
