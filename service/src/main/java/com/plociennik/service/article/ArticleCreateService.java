package com.plociennik.service.article;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.TagEntity;
import com.plociennik.model.repository.article.ArticleRepository;
import com.plociennik.service.article.dto.ArticleCreate;
import com.plociennik.service.article.mapper.ArticleCreateMapper;
import com.plociennik.service.tag.TagHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleCreateService {

    private final ArticleRepository articleRepository;
    private final ArticleCreateMapper articleCreateMapper;
    private final TagHelper tagHelper;

    public ArticleCreateService(
            ArticleRepository articleRepository,
            TagHelper tagHelper) {
        this.articleRepository = articleRepository;
        this.articleCreateMapper = new ArticleCreateMapper();
        this.tagHelper = tagHelper;
    }

    public String create(ArticleCreate articleCreate) {
        List<TagEntity> tags = tagHelper.mergeExistingTagsWithNewTags(articleCreate.getTags());
        ArticleEntity articleEntity = articleCreateMapper.mapToEntity(articleCreate, tags);
        ArticleEntity save = articleRepository.save(articleEntity);
        return save.getId().toString();
    }
}
