package com.plociennik.common.errorhandling.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class InkoValidationException extends RuntimeException {

    private final Map<String, String> errors;

    public InkoValidationException(Map<String, String> errors) {
        super("Validation failed!");
        this.errors = errors;
    }
}
