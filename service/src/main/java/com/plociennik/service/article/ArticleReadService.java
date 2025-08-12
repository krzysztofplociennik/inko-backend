package com.plociennik.service.article;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.ArticleType;
import com.plociennik.model.repository.article.ArticleRepository;
import com.plociennik.service.article.dto.ArticleDetails;
import com.plociennik.service.article.mapper.ArticleReadMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleReadService {

    private final ArticleRepository articleRepository;
    private final ArticleReadMapper articleReadMapper;

    public ArticleReadService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
        this.articleReadMapper = new ArticleReadMapper();
    }

    public ArticleDetails getArticleDetails(String id) throws Exception {
        UUID uuid = UUID.fromString(id);
        Optional<ArticleEntity> searchedArticle = articleRepository.findById(uuid);
        if (searchedArticle.isPresent()) {
            ArticleEntity articleEntity = searchedArticle.get();
            return articleReadMapper.mapToDetails(articleEntity);
        } else {
            throw new Exception("There is no article with this ID: '" + id + "' in the database! (eid: 310720240701");
        }
    }

    public List<String> getAllTypes() {
        ArticleType[] values = ArticleType.values();
        return Arrays.stream(values)
                .map(Enum::toString)
                .toList();
    }
}
