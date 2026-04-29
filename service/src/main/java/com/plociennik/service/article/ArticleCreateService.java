package com.plociennik.service.article;

import com.plociennik.common.errorhandling.exceptions.InkoRuntimeException;
import com.plociennik.model.ArticleEntity;
import com.plociennik.model.repository.article.ArticleRepository;
import com.plociennik.service.article.dto.ArticleCreate;
import com.plociennik.service.article.mapper.ArticleCreateMapper;
import com.plociennik.service.validation.ValidationManager;
import org.springframework.stereotype.Service;


@Service
public class ArticleCreateService {

    private final ArticleRepository articleRepository;
    private final ArticleCreateMapper articleCreateMapper;
    private final ValidationManager validationManager;

    public ArticleCreateService(
            ArticleRepository articleRepository,
            ValidationManager validationManager
    ) {
        this.articleRepository = articleRepository;
        this.articleCreateMapper = new ArticleCreateMapper();
        this.validationManager = validationManager;
    }

    public String create(ArticleCreate articleCreate) {
        if (articleCreate == null) {
            throw new InkoRuntimeException("202628041003", "ArticleCreate is null!");
        }
        validationManager.validateArticleCreate(articleCreate);
        ArticleEntity articleEntity = articleCreateMapper.mapToEntity(articleCreate);
        ArticleEntity save = articleRepository.save(articleEntity);
        return save.getId().toString();
    }
}
