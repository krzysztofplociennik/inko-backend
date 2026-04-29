package com.plociennik.service.article.validation;

import com.plociennik.service.article.dto.ArticleCreate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ContentRequiredValidator implements ArticleCreateValidator {

    @Override
    public boolean isValid(ArticleCreate articleCreate) {
        String content = articleCreate.getContent();
        return StringUtils.isNotBlank(content);
    }

    @Override
    public String createValidationFailureMessage() {
        return "[EID: 202629041016] Content is blank!";
    }
}