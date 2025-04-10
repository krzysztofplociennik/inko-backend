package com.plociennik.service.importing.validation;

import com.plociennik.common.util.ValidationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class TypeInFileContentNotBlankFileValidator implements ImportingFileValidator {

    @Override
    public boolean isValid(MultipartFile multipartFile) {
        String contentFromFile = ValidationUtils.extractContentFromFile(multipartFile);
        String extractedType = extractType(contentFromFile);
        return StringUtils.isNotBlank(extractedType);
    }

    private String extractType(String contentFromFile) {
        String[] splitContent = contentFromFile.split("\n");
        return splitContent[2].trim().substring(5);
    }

    @Override
    public String createValidationFailureMessage() {
        return "Type is blank! (EID: 202509041224)";
    }
}
