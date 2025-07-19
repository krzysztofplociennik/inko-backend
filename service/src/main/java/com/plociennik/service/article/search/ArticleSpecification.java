package com.plociennik.service.article.search;

import com.plociennik.model.ArticleEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public class ArticleSpecification {

    public static Specification<ArticleEntity> filterBy(ArticleFilter filter) {
        return Specification
                .where(hasSearchPhrase(filter.getSearchPhrase()))
                .and(hasType(filter.getType()))
                .and(hasTags(filter.getTags()))
                .and(hasCreationDateBetween(filter.getCreationDateFrom(), filter.getCreationDateTo()));
    }

    private static Specification<ArticleEntity> hasSearchPhrase(String searchPhrase) {
        return (root, query, cb) -> {
            if (searchPhrase == null || searchPhrase.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("title")),
                    "%" + searchPhrase.toLowerCase() + "%");
        };
    }

    private static Specification<ArticleEntity> hasType(String type) {
        return (root, query, cb) ->
                type == null ? cb.conjunction() : cb.equal(root.get("type"), type);
    }

    private static Specification<ArticleEntity> hasTags(List<String> tags) {
        return (root, query, cb) -> {
            if (tags == null || tags.isEmpty()) {
                return cb.conjunction();
            }
            return root.join("tags").in(tags);
        };
    }

    private static Specification<ArticleEntity> hasCreationDateBetween(Date from, Date to) {
        return (root, query, cb) -> {
            if (from == null && to == null) return cb.conjunction();
            if (from == null) return cb.lessThanOrEqualTo(root.get("creationDate"), to);
            if (to == null) return cb.greaterThanOrEqualTo(root.get("creationDate"), from);
            return cb.between(root.get("creationDate"), from, to);
        };
    }
}