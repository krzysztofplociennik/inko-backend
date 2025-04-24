package com.plociennik.service.other;

import com.plociennik.model.repository.article.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KeepAliveService {

    private final ArticleRepository articleRepository;

    public void findFirstArticle() {
        this.articleRepository.findAll();
    }
}
