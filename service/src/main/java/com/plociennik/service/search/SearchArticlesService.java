package com.plociennik.service.search;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.repository.ArticleRepository;
import com.plociennik.service.search.dto.SearchArticlesItem;
import com.plociennik.service.search.mapper.SearchArticlesMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchArticlesService {

    private final ArticleRepository articleRepository;
    private final SearchArticlesMapper searchArticlesMapper;

    public SearchArticlesService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
        this.searchArticlesMapper = new SearchArticlesMapper();
    }

    public List<SearchArticlesItem> search() {
        List<ArticleEntity> all = articleRepository.findAll();

        List<SearchArticlesItem> searchedArticles = all.stream()
                .map(searchArticlesMapper::mapToRead)
                .toList();

        return searchedArticles;
    }
}
