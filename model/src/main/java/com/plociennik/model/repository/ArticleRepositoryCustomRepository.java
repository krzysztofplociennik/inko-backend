package com.plociennik.model.repository;

import com.plociennik.model.ArticleEntity;

import java.util.List;

public interface ArticleRepositoryCustomRepository {

    List<ArticleEntity> findByPhrase(String phrase);
}
