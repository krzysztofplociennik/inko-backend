package com.plociennik.service.importing.validation;

import com.plociennik.common.errorhandling.exceptions.InkoRuntimeException;
import com.plociennik.service.importing.dto.ImportFilesRequestBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class CorrectContentStructureValidator implements ImportingValidator {

    @Override
    public boolean isValid(ImportFilesRequestBody requestBody) {

        List<MultipartFile> files = requestBody.getFiles();
        for (MultipartFile file : files) {
            boolean isValid = hasValidStructure(file);
            if (!isValid) {
                return false;
            }
        }
        return true;
    }

    private boolean hasValidStructure(MultipartFile file) {
        String content = extractContentFromFile(file);

        if (StringUtils.isBlank(content)) {
            return false;
        }

        String[] splitContent = content.split("\n");

        return hasUUUIDPartInCorrectPlace(splitContent)
                && hasTitlePartInCorrectPlace(splitContent)
                && hasTypePartInCorrectPlace(splitContent)
                && hasDateOfCreationPartInCorrectPlace(splitContent)
                && hasDateOfModificationPartInCorrectPlace(splitContent)
                && hasTagsPartInCorrectPlace(splitContent)
                && hasContentPartInCorrectPlace(splitContent);
    }

    private String extractContentFromFile(MultipartFile file) {
        String content = null;
        try {
            content = new String(file.getBytes());
        } catch (IOException e) {
            throw new InkoRuntimeException(e.getMessage(), "202504031243");
        }
        return content;
    }

    private boolean hasUUUIDPartInCorrectPlace(String[] splitContent) {
        String expectedUUIDPart = splitContent[0].trim();
        return expectedUUIDPart.startsWith("UUID: ");
    }

    private boolean hasTitlePartInCorrectPlace(String[] splitContent) {
        String expectedUUIDPart = splitContent[1].trim();
        return expectedUUIDPart.startsWith("Title: ");
    }

    private boolean hasTypePartInCorrectPlace(String[] splitContent) {
        String expectedUUIDPart = splitContent[2].trim();
        return expectedUUIDPart.startsWith("Type: ");
    }

    private boolean hasDateOfCreationPartInCorrectPlace(String[] splitContent) {
        String expectedUUIDPart = splitContent[3].trim();
        return expectedUUIDPart.startsWith("Date of creation: ");
    }

    private boolean hasDateOfModificationPartInCorrectPlace(String[] splitContent) {
        String expectedUUIDPart = splitContent[4].trim();
        return expectedUUIDPart.startsWith("Date of modification: ");
    }

    private boolean hasTagsPartInCorrectPlace(String[] splitContent) {
        String expectedUUIDPart = splitContent[5].trim();
        return expectedUUIDPart.startsWith("Tags: ");
    }

    private boolean hasContentPartInCorrectPlace(String[] splitContent) {
        String expectedUUIDPart = splitContent[7].trim();
        return expectedUUIDPart.startsWith("Content:");
    }

    @Override
    public String createValidationFailureMessage() {
        return """
                One of the files in the request does not have the correct structure of:
                
                UUID: ...
                Title: ...
                Type: ...
                Date of creation: ...
                Date of modification: ...
                Tags: ...
                
                Content: ...
                """;
    }
}
