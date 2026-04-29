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

import java.util.List;

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
                throwErrorIfFileNotValid(validator, file);
            }
        }
    }

    public void validateArticleCreate(ArticleCreate articleCreate) {
        for (ArticleCreateValidator validator : articleCreateValidators) {
            boolean isValid = validator.isValid(articleCreate);
            if (!isValid) {
                throwErrorIfFileNotValid(validator, articleCreate);
            }
        }
    }

    private void throwErrorIfFileNotValid(ImportingFileValidator validator, MultipartFile file) {
        String prefix = "[InkoImportException] ";
        String validationFailureMessage = validator.createValidationFailureMessage();
        log.error(validationFailureMessage);
        throw new InkoImportException(prefix + validationFailureMessage, file);
    }

    private void throwErrorIfFileNotValid(ArticleCreateValidator validator, ArticleCreate file) {
        String prefix = "[InkoValidationException] ";
        String validationFailureMessage = validator.createValidationFailureMessage();
        log.error(validationFailureMessage);
        throw new InkoValidationException(prefix + validationFailureMessage);
    }
}
