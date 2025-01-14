package com.plociennik.model.repository.article;

import com.plociennik.common.errorhandling.exceptions.ArticleNotFoundException;
import com.plociennik.model.ArticleEntity;
import com.plociennik.model.QArticleEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class ArticleCustomRepositoryImpl implements ArticleCustomRepository {

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

    @Override
    @SneakyThrows
    public ArticleEntity findByUUID(UUID uuid) {
        JPAQuery<ArticleEntity> query = new JPAQuery<>(this.entityManager);
        QArticleEntity qArticleEntity = QArticleEntity.articleEntity;
        BooleanExpression entityWithUUID = qArticleEntity.id.eq(uuid);

        ArticleEntity articleEntity = query.from(qArticleEntity)
                .where(entityWithUUID)
                .fetchOne();

        if (articleEntity == null) {
            throw new ArticleNotFoundException(uuid.toString());
        }

        return articleEntity;
    }


}
