package com.plociennik.service.validation;

import com.plociennik.common.errorhandling.exceptions.InkoValidationException;
import com.plociennik.service.importing.dto.ImportFilesRequestBody;
import com.plociennik.service.importing.validation.ImportValidator;
import com.plociennik.service.importing.validation.ImportingFileValidator;
import com.plociennik.service.importing.validation.ImportFilesRequestValidator;
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
    private List<ImportFilesRequestValidator> importFilesRequestValidators;

    public void validateImporting(ImportFilesRequestBody requestBody) {
        validateRequest(requestBody);
        validateEveryFileInRequest(requestBody);
    }

    private void validateRequest(ImportFilesRequestBody requestBody) {
        importFilesRequestValidators.forEach(validator -> {
            boolean isRequestValid = validator.isValid(requestBody);
            if (!isRequestValid) {
                throwErrorIfNotValid(validator);
            }
        });
    }

    private void validateEveryFileInRequest(ImportFilesRequestBody requestBody) {
        List<MultipartFile> filesToValidate = requestBody.getFiles();
        filesToValidate.forEach(this::validateSingleFile);
    }

    private void validateSingleFile(MultipartFile file) {
        for (ImportingFileValidator validator : importingFileValidators) {
            boolean isFileValid = validator.isValid(file);
            if (!isFileValid) {
                throwErrorIfNotValid(validator);
            }
        }
    }

    private void throwErrorIfNotValid(ImportValidator validator) {
        String prefix = "[InkoValidationException] ";
        String validationFailureMessage = validator.createValidationFailureMessage();
        log.error(validationFailureMessage);
        throw new InkoValidationException(prefix + validationFailureMessage);
    }
}
