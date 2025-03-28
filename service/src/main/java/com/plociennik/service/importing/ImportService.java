package com.plociennik.service.importing;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.repository.article.ArticleRepository;
import org.springframework.stereotype.Service;

@Service
public class ImportService {

    private final ArticleRepository articleRepository;

    public ImportService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public String importFiles() {
        return "importing files...";
    }

    ArticleEntity mapFileIntoEntity() {
        return null;
    }
}
