package com.plociennik.service.importing.validation;

import com.plociennik.service.importing.dto.ImportFilesRequestBody;

public interface ImportingValidator {
    boolean isValid(ImportFilesRequestBody requestBody);
    String createValidationFailureMessage();
}
