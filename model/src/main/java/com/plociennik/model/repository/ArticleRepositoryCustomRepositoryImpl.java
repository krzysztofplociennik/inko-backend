package com.plociennik.model.repository;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.QArticleEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ArticleRepositoryCustomRepositoryImpl implements ArticleRepositoryCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<ArticleEntity> findByPhrase(String phrase) {

        JPAQuery<ArticleEntity> query = new JPAQuery<>(this.entityManager);

        QArticleEntity qArticleEntity = QArticleEntity.articleEntity;

        BooleanExpression contentContainsPhrase = qArticleEntity.content.contains(phrase);
        BooleanExpression titleContainsPhrase = qArticleEntity.title.contains(phrase);

        List<ArticleEntity> foundArticles = query.from(qArticleEntity)
                .where(contentContainsPhrase.or(titleContainsPhrase))
                .fetch();

        return foundArticles;
    }
}
