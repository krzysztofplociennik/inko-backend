package com.plociennik.service.article.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;

public class ArticleUpdateContentNotEmpty implements ConstraintValidator<ValidContent, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        String strippedWithRegex = value.replaceAll("<[^>]*>", "").trim();
        String strippedWithJsoup = Jsoup.parse(strippedWithRegex).text().trim();
        return StringUtils.isNotBlank(strippedWithJsoup);
    }
}
