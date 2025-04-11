package com.plociennik.service.validation;

import com.plociennik.common.errorhandling.exceptions.InkoImportException;
import com.plociennik.service.importing.validation.ImportingFileValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class ValidationManager {

    private List<ImportingFileValidator> importingFileValidators;

    public void validateFile(MultipartFile file) {
        for (ImportingFileValidator validator : importingFileValidators) {
            boolean isFileValid = validator.isValid(file);
            if (!isFileValid) {
                throwErrorIfFileNotValid(validator, file);
            }
        }
    }

    private void throwErrorIfFileNotValid(ImportingFileValidator validator, MultipartFile file) {
        String prefix = "[InkoImportException] ";
        String validationFailureMessage = validator.createValidationFailureMessage();
        log.error(validationFailureMessage);
        throw new InkoImportException(prefix + validationFailureMessage, file);
    }
}
