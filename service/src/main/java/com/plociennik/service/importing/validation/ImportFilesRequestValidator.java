package com.plociennik.service.importing.validation;

import com.plociennik.service.importing.dto.ImportFilesRequestBody;

public interface ImportFilesRequestValidator extends ImportValidator {
    boolean isValid(ImportFilesRequestBody requestBody);
}
