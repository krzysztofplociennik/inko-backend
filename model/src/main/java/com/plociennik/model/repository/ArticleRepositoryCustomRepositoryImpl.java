package com.plociennik.model.repository;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.QArticleEntity;
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

        List<ArticleEntity> fetch = query.from(qArticleEntity)
                .where(qArticleEntity.content.contains(phrase))
                .fetch();

        return fetch;
    }
}
