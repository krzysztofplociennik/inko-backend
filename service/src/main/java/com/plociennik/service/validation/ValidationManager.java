package com.plociennik.service.validation;

import com.plociennik.common.errorhandling.exceptions.InkoImportException;
import com.plociennik.common.errorhandling.exceptions.InkoValidationException;
import com.plociennik.service.article.dto.ArticleCreate;
import com.plociennik.service.article.validation.ArticleCreateValidator;
import com.plociennik.service.importing.validation.ImportingFileValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class ValidationManager {

    private List<ImportingFileValidator> importingFileValidators;
    private List<ArticleCreateValidator> articleCreateValidators;

    public void validateFile(MultipartFile file) {
        for (ImportingFileValidator validator : importingFileValidators) {
            boolean isFileValid = validator.isValid(file);
            if (!isFileValid) {
                throwError(validator, file);
            }
        }
    }

    public void validateArticleCreate(ArticleCreate articleCreate) {
        Map<String, String> errors = new LinkedHashMap<>();

        for (ArticleCreateValidator validator : articleCreateValidators) {
            if (!validator.isValid(articleCreate)) {
                String validationFailureMessage = validator.createValidationFailureMessage();
                log.error("[InkoValidationException] {}: {}", validator.getPath(), validationFailureMessage);
                errors.put(validator.getPath(), validationFailureMessage);
            }
        }

        if (!errors.isEmpty()) {
            throw new InkoValidationException(errors);
        }
    }

    private void throwError(ImportingFileValidator validator, MultipartFile file) {
        String prefix = "[InkoImportException] ";
        String validationFailureMessage = validator.createValidationFailureMessage();
        log.error(validationFailureMessage);
        throw new InkoImportException(prefix + validationFailureMessage, file);
    }
}
