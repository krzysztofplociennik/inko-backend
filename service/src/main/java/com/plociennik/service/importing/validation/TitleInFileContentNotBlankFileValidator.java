package com.plociennik.service.importing.validation;

import com.plociennik.common.util.ValidationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class TitleInFileContentNotBlankFileValidator implements ImportingFileValidator {

    @Override
    public boolean isValid(MultipartFile multipartFile) {
        String contentFromFile = ValidationUtils.extractContentFromFile(multipartFile);
        String extractedTitle = extractTitle(contentFromFile);
        return StringUtils.isNotBlank(extractedTitle);
    }

    private String extractTitle(String contentFromFile) {
        String[] splitContent = contentFromFile.split("\n");
        return splitContent[1].trim().substring(6);
    }

    @Override
    public String createValidationFailureMessage() {
        return "Title is blank! (EID: 202509041223)";
    }
}
