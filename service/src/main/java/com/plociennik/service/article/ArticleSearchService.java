package com.plociennik.service.article;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.repository.article.ArticleCustomRepositoryImpl;
import com.plociennik.service.article.search.ArticleFilter;
import com.plociennik.service.article.search.ArticleSpecification;
import com.plociennik.service.autocomplete.AutocompleteService;
import com.plociennik.service.article.dto.SearchArticlesItem;
import com.plociennik.service.article.mapper.ArticleSearchMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<SearchArticlesItem> search(ArticleFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());

        Specification<ArticleEntity> spec = Specification
                .where(ArticleSpecification.filterBy(filter))
                .and(hasSearchPhrase(filter.getSearchPhrase()));

        Page<ArticleEntity> articlesFound = articleRepositoryCustomRepositoryImpl.findBySpecification(spec, pageable);

        List<SearchArticlesItem> mappedArticles = articlesFound.stream()
                .map(articleSearchMapper::mapToRead)
                .toList();

        return new PageImpl<>(mappedArticles, pageable, mappedArticles.size());
    }

    private Specification<ArticleEntity> hasSearchPhrase(String searchPhrase) {
        return (root, query, cb) -> {
            if (searchPhrase == null || searchPhrase.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("title")),
                    "%" + searchPhrase.toLowerCase() + "%");
        };
    }
}
