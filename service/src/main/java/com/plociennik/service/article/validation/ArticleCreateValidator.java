package com.plociennik.service.article.validation;

import com.plociennik.service.article.dto.ArticleCreate;

public interface ArticleCreateValidator {
    boolean isValid(ArticleCreate articleCreate);
    String createValidationFailureMessage();
}