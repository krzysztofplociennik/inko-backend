package com.plociennik.service.validation;

import com.plociennik.common.errorhandling.exceptions.InkoValidationException;
import com.plociennik.service.importing.dto.ImportFilesRequestBody;
import com.plociennik.service.importing.validation.ImportingValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
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
        log.error(validationFailureMessage);
        throw new InkoValidationException(prefix + validationFailureMessage);
    }
}
