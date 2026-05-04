package com.plociennik.service.article.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ArticleUpdateContentNotEmpty.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidContent {
    String message() default "Content is empty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}