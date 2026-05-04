package com.plociennik.service.article.validation;

import com.plociennik.service.article.dto.ArticleCreate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class TitleRequiredValidator implements ArticleCreateValidator {

    @Override
    public String getPath() {
        return "ArticleCreate:title";
    }

    @Override
    public boolean isValid(ArticleCreate articleCreate) {
        String title = articleCreate.getTitle();
        return StringUtils.isNotBlank(title);
    }

    @Override
    public String createValidationFailureMessage() {
        return "[EID: 202629041022] Title is blank!";
    }
}
