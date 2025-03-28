package com.plociennik.service.article;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.repository.article.ArticleCustomRepositoryImpl;
import com.plociennik.service.autocomplete.AutocompleteService;
import com.plociennik.service.article.dto.SearchArticlesItem;
import com.plociennik.service.article.mapper.ArticleSearchMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleSearchService {

    private final ArticleCustomRepositoryImpl articleRepositoryCustomRepositoryImpl;
    private final ArticleSearchMapper articleSearchMapper;
    private final AutocompleteService autocompleteService;

    public ArticleSearchService(
            ArticleCustomRepositoryImpl articleRepositoryCustomRepositoryImpl,
            AutocompleteService autocompleteService
    ) {
        this.articleRepositoryCustomRepositoryImpl = articleRepositoryCustomRepositoryImpl;
        this.articleSearchMapper = new ArticleSearchMapper();
        this.autocompleteService = autocompleteService;
    }

    public List<SearchArticlesItem> search(String searchPhrase) {
        autocompleteService.incrementUsageIfExists(searchPhrase);

        List<ArticleEntity> articlesByPhrase = articleRepositoryCustomRepositoryImpl.findByPhrase(searchPhrase);

        List<SearchArticlesItem> searchedArticles = articlesByPhrase.stream()
                .map(articleSearchMapper::mapToRead)
                .toList();

        return searchedArticles;
    }
}
