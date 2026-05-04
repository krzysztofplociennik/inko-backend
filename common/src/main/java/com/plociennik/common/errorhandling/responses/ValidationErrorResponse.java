package com.plociennik.common.errorhandling.responses;

import lombok.Getter;

import java.util.Map;

@Getter
public class ValidationErrorResponse extends ErrorResponse {

    private final String invalidObject;
    private final Map<String, String> errors;

    public ValidationErrorResponse(int status, String message, String invalidObject, Map<String, String> errors) {
        super(status, message);
        this.invalidObject = invalidObject;
        this.errors = errors;
    }
}
