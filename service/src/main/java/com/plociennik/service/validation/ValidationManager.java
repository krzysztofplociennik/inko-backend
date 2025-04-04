package com.plociennik.service.validation;

import com.plociennik.common.errorhandling.exceptions.InkoValidationException;
import com.plociennik.service.importing.dto.ImportFilesRequestBody;
import com.plociennik.service.importing.validation.ImportingValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ValidationManager {

    private List<ImportingValidator> importingValidators;

    public void validateImporting(ImportFilesRequestBody requestBody) {
        for (ImportingValidator validator : importingValidators) {
            boolean isValid = validator.isValid(requestBody);
            if (!isValid) {
                throwErrorIfNotValid(validator);
            }
        }
    }

    private void throwErrorIfNotValid(ImportingValidator validator) {
        String prefix = "[InkoValidationException] ";
        String validationFailureMessage = validator.createValidationFailureMessage();
        throw new InkoValidationException(prefix + validationFailureMessage);
    }
}
