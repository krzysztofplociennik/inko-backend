package com.plociennik.service.article.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ArticleUpdateTypeRequiredValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidType {
    String message() default "Type is not recognized";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
