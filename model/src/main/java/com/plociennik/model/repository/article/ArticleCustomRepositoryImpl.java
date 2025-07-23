package com.plociennik.model.repository.article;

import com.plociennik.common.errorhandling.exceptions.ArticleNotFoundException;
import com.plociennik.model.ArticleEntity;
import com.plociennik.model.QArticleEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class ArticleCustomRepositoryImpl implements ArticleCustomRepository {

    private final EntityManager entityManager;
    private final ArticleRepository articleRepository;

    @Override
    public List<ArticleEntity> findByPhrase(String phrase) {

        JPAQuery<ArticleEntity> query = new JPAQuery<>(this.entityManager);

        QArticleEntity qArticleEntity = QArticleEntity.articleEntity;

        BooleanExpression contentContainsPhrase = qArticleEntity.content.contains(phrase);
        BooleanExpression titleContainsPhrase = qArticleEntity.title.contains(phrase);

        return query.from(qArticleEntity)
                .where(contentContainsPhrase.or(titleContainsPhrase))
                .fetch();
    }

    @Override
    public ArticleEntity findByUUID(UUID uuid) throws ArticleNotFoundException {
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

    @Override
    public Page<ArticleEntity> findBySpecification(Specification<ArticleEntity> specification, Pageable pageable) {
        return articleRepository.findAll(specification, pageable);
    }


}
