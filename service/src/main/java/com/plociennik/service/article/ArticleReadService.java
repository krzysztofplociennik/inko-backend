package com.plociennik.service.article;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.ArticleType;
import com.plociennik.model.repository.article.ArticleRepository;
import com.plociennik.service.article.dto.AllArticlesItem;
import com.plociennik.service.article.dto.ArticleDetails;
import com.plociennik.service.article.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleReadService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    public ArticleReadService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
        this.articleMapper = new ArticleMapper();
    }

    public List<AllArticlesItem> getAll() {
        List<AllArticlesItem> collect = articleRepository.findAll()
                .stream()
                .map(articleMapper::mapToAllItem)
                .collect(Collectors.toList());
        return collect;
    }

    public ArticleDetails getArticleDetails(String id) throws Exception {
        UUID uuid = UUID.fromString(id);
        Optional<ArticleEntity> searchedArticle = articleRepository.findById(uuid);
        if (searchedArticle.isPresent()) {
            ArticleEntity articleEntity = searchedArticle.get();
            ArticleDetails articleDetails = articleMapper.mapToDetails(articleEntity);
            return articleDetails;
        } else {
            throw new Exception("There is no article with this ID: '" + id + "' in the database! (eid: 310720240701");
        }
    }

    public List<String> getAllTypes() {
        ArticleType[] values = ArticleType.values();
        List<String> articleTypes = Arrays.stream(values)
                .map(Enum::toString)
                .toList();
        return articleTypes;
    }

}
