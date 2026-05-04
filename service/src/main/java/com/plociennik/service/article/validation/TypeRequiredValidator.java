package com.plociennik.service.article.validation;

import com.plociennik.common.errorhandling.exceptions.InkoRuntimeException;
import com.plociennik.model.ArticleType;
import com.plociennik.service.article.dto.ArticleCreate;
import org.springframework.stereotype.Component;

@Component
public class TypeRequiredValidator implements ArticleCreateValidator {

    @Override
    public String getPath() {
        return "ArticleCreate:type";
    }

    @Override
    public boolean isValid(ArticleCreate articleCreate) {
        String type = articleCreate.getType();
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

    @Override
    public String createValidationFailureMessage() {
        return "[EID: 202629041028] Type is not recognized!";
    }
}
