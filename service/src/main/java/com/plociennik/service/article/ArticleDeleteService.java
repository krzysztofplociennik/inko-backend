package com.plociennik.service.article;

import com.plociennik.model.repository.article.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ArticleDeleteService {

    private final ArticleRepository articleRepository;

    public ArticleDeleteService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        articleRepository.deleteById(uuid);
    }
}
