package com.plociennik.service.article;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.repository.article.ArticleRepositoryCustomRepositoryImpl;
import com.plociennik.service.autocomplete.AutocompleteService;
import com.plociennik.service.article.dto.SearchArticlesItem;
import com.plociennik.service.article.mapper.SearchArticlesMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchArticlesService {

    private final ArticleRepositoryCustomRepositoryImpl articleRepositoryCustomRepositoryImpl;
    private final SearchArticlesMapper searchArticlesMapper;
    private final AutocompleteService autocompleteService;

    public SearchArticlesService(
            ArticleRepositoryCustomRepositoryImpl articleRepositoryCustomRepositoryImpl,
            AutocompleteService autocompleteService
    ) {
        this.articleRepositoryCustomRepositoryImpl = articleRepositoryCustomRepositoryImpl;
        this.searchArticlesMapper = new SearchArticlesMapper();
        this.autocompleteService = autocompleteService;
    }

    public List<SearchArticlesItem> search(String searchPhrase) {

        autocompleteService.incrementUsageIfExists(searchPhrase);

        List<ArticleEntity> byPhrase = articleRepositoryCustomRepositoryImpl.findByPhrase(searchPhrase);

        List<SearchArticlesItem> searchedArticles = byPhrase.stream()
                .map(searchArticlesMapper::mapToRead)
                .toList();

        return searchedArticles;
    }
}
