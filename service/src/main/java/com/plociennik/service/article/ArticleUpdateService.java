package com.plociennik.service.article;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.repository.article.ArticleCustomRepository;
import com.plociennik.model.repository.article.ArticleRepository;
import com.plociennik.service.article.dto.ArticleDetails;
import com.plociennik.service.article.dto.ArticleUpdate;
import com.plociennik.service.article.mapper.ArticleReadMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ArticleUpdateService {

    private final ArticleRepository articleRepository;
    private final ArticleCustomRepository articleCustomRepository;
    private final ArticleReadMapper articleReadMapper;

    public ArticleUpdateService(ArticleRepository articleRepository, ArticleCustomRepository articleCustomRepository) {
        this.articleRepository = articleRepository;
        this.articleCustomRepository = articleCustomRepository;
        this.articleReadMapper = new ArticleReadMapper();
    }

    public ArticleDetails update(ArticleUpdate articleUpdate) {
        LocalDateTime modificationDate = LocalDateTime.now();

        UUID uuid = UUID.fromString(articleUpdate.getId());
        ArticleEntity entity = articleCustomRepository.findByUUID(uuid);

        entity.setTitle(articleUpdate.getTitle());
        entity.setContent(articleUpdate.getContent());
        entity.setModificationDate(modificationDate);

        ArticleEntity updatedArticle = articleRepository.save(entity);

        ArticleDetails response = articleReadMapper.mapToDetails(updatedArticle);

        return response;
    }
}
