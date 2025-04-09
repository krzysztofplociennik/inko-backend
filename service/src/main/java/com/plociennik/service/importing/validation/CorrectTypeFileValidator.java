package com.plociennik.service.importing.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class CorrectTypeFileValidator implements ImportingFileValidator {

    private final static List<String> CORRECT_EXTENSIONS = List.of(
            ".txt", ".TXT"
    );
    private final static List<String> CORRECT_TYPES = List.of(
            "text/plain"
    );

    @Override
    public boolean isValid(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        return isFileOfCorrectExtensionAndType(originalFilename, contentType);
    }

    boolean isFileOfCorrectExtensionAndType(String filename, String contentType) {
        if (StringUtils.isBlank(filename) || StringUtils.isBlank(contentType)) {
            return false;
        }

        String extension = getExtension(filename);
        boolean isExtensionValid = CORRECT_EXTENSIONS.contains(extension);

        boolean isContentTypeValid = CORRECT_TYPES.contains(contentType);

        return isExtensionValid && isContentTypeValid;
    }

    private String getExtension(String filename) {
        int length = filename.length();
        int indexOfDot = -1;

        for (int i = length - 1; i >= 0; i--) {
            char c = filename.charAt(i);
            if ('.' == c) {
                indexOfDot = i;
                break;
            }
        }
        if (indexOfDot == -1) {
            return "";
        }
        return filename.substring(indexOfDot, length);
    }

    @Override
    public String createValidationFailureMessage() {
        return "One of the files has an invalid type! (EID: 202504010904)";
    }
}
