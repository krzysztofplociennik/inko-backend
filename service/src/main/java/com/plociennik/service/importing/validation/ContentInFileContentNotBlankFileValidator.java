package com.plociennik.service.importing.validation;

import com.plociennik.common.util.ValidationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ContentInFileContentNotBlankFileValidator implements ImportingFileValidator {

    @Override
    public boolean isValid(MultipartFile multipartFile) {
        String contentFromFile = ValidationUtils.extractContentFromFile(multipartFile);
        String extractedContent = extractContent(contentFromFile);
        return StringUtils.isNotBlank(extractedContent);
    }

    private String extractContent(String contentFromFile) {
        String[] splitContent = contentFromFile.split("\n");

        StringBuilder sb = new StringBuilder();
        for (int i = 9; i < splitContent.length; i++) {
            sb.append(splitContent[i]);
        }
        return sb.toString();
    }

    @Override
    public String createValidationFailureMessage() {
        return "Content is blank! (EID: 202509041221)";
    }
}
