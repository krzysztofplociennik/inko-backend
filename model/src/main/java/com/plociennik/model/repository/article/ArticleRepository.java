package com.plociennik.model.repository.article;

import com.plociennik.model.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID>, JpaSpecificationExecutor<ArticleEntity> {
}
