package com.plociennik.model.repository.article;

import com.plociennik.common.errorhandling.exceptions.ArticleNotFoundException;
import com.plociennik.model.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface ArticleCustomRepository {

    List<ArticleEntity> findByPhrase(String phrase);
    ArticleEntity findByUUID(UUID uuid) throws ArticleNotFoundException;
    Page<ArticleEntity> findBySpecification(Specification<ArticleEntity> specification, Pageable pageable);
}
