package com.plociennik.service.importing.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DateOfCreationInFileContentNotBlankFileValidator implements ImportingFileValidator {

    @Override
    public boolean isValid(MultipartFile multipartFile) {
        String contentFromFile = ValidationUtils.extractContentFromFile(multipartFile);
        String extractedDate = extractDate(contentFromFile);
        return StringUtils.isNotBlank(extractedDate);
    }

    private String extractDate(String contentFromFile) {
        String[] splitContent = contentFromFile.split("\n");
        return splitContent[3].trim().substring(17);
    }

    @Override
    public String createValidationFailureMessage() {
        return "Date of creation is blank! (EID: 202509041222)";
    }
}
