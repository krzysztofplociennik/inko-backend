package com.plociennik.model.repository.article;

import com.plociennik.model.ArticleEntity;

import java.util.List;
import java.util.UUID;

public interface ArticleRepositoryCustomRepository {

    List<ArticleEntity> findByPhrase(String phrase);
    ArticleEntity findByUUID(UUID uuid);
}
