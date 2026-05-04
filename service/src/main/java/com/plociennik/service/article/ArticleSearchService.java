package com.plociennik.service.article;

import com.plociennik.common.errorhandling.exceptions.InkoRuntimeException;
import com.plociennik.model.ArticleEntity;
import com.plociennik.model.repository.article.ArticleCustomRepositoryImpl;
import com.plociennik.service.article.search.ArticleFilter;
import com.plociennik.service.article.search.ArticleSpecification;
import com.plociennik.service.autocomplete.AutocompleteService;
import com.plociennik.service.article.dto.SearchArticlesItem;
import com.plociennik.service.article.mapper.ArticleSearchMapper;
import org.springframework.data.domain.*;
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
        Specification<ArticleEntity> spec = ArticleSpecification.createWith(filter);
        Sort sort = Sort.by(getSortDirection(filter), translateFieldFromLabel(filter));
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ArticleEntity> articlesFound = articleRepositoryCustomRepositoryImpl.findBySpecification(spec, pageable);

        List<SearchArticlesItem> mappedArticles = articlesFound.stream()
                .map(articleSearchMapper::mapToRead)
                .toList();

        return new PageImpl<>(mappedArticles, pageable, articlesFound.getTotalElements());
    }

    private Sort.Direction getSortDirection(ArticleFilter articleFilter) {
        String lowercaseSortType = articleFilter.getSort().getSortType().toLowerCase();
        return lowercaseSortType.startsWith("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

    private String translateFieldFromLabel(ArticleFilter filter) {
        String sort = filter.getSort().getSortField();
        return switch (sort) {
            case "title", "type" -> sort;
            case "date of creation" -> "creationDate";
            case "date of modification" -> "modificationDate";
            default ->
                    throw new InkoRuntimeException("081120251217", "There was an error while translating the sorting field: [" + sort + "]");
        };
    }
}
