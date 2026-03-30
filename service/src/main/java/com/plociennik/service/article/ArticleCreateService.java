package com.plociennik.service.article;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.repository.article.ArticleRepository;
import com.plociennik.service.article.dto.ArticleCreate;
import com.plociennik.service.article.mapper.ArticleCreateMapper;
import org.springframework.stereotype.Service;


@Service
public class ArticleCreateService {

    private final ArticleRepository articleRepository;
    private final ArticleCreateMapper articleCreateMapper;

    public ArticleCreateService(
            ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
        this.articleCreateMapper = new ArticleCreateMapper();
    }

    public String create(ArticleCreate articleCreate) {
        ArticleEntity articleEntity = articleCreateMapper.mapToEntity(articleCreate);
        ArticleEntity save = articleRepository.save(articleEntity);
        return save.getId().toString();
    }
}
