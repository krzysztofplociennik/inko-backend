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

    public Page<SearchArticlesItem> search(int page, int size, ArticleFilter filter) {
        autocompleteService.incrementUsageIfExists(filter.getSearchPhrase());
        Pageable pageable = PageRequest.of(page, size);

        Specification<ArticleEntity> spec = Specification.where(ArticleSpecification.filterBy(filter));

        Page<ArticleEntity> articlesFound = articleRepositoryCustomRepositoryImpl.findBySpecification(spec, pageable);

        List<SearchArticlesItem> mappedArticles = articlesFound.stream()
                .map(articleSearchMapper::mapToRead)
                .toList();

        return new PageImpl<>(mappedArticles, pageable, mappedArticles.size());
    }
}
