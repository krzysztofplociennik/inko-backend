package com.plociennik.common.errorhandling.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationErrorResponse extends ErrorResponse {

    private String invalidObject;

    public ValidationErrorResponse(int status, String message, String invalidObject) {
        super(status, message);
        this.invalidObject = invalidObject;
    }
}
