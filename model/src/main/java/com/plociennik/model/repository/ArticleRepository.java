package com.plociennik.model.repository;

import com.plociennik.model.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {
}
