package com.plociennik.service.article.validation;

import com.plociennik.common.errorhandling.exceptions.InkoRuntimeException;
import com.plociennik.model.ArticleType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ArticleUpdateTypeRequiredValidator implements ConstraintValidator<ValidType, String> {

    @Override
    public boolean isValid(String type, ConstraintValidatorContext context) {
        if (type == null) {
            return false;
        }

        try {
            ArticleType.getType(type);
        } catch (InkoRuntimeException e) {
            return false;
        }
        return true;
    }

}
