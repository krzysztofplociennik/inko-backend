package com.plociennik.service.importing.validation;

import com.plociennik.common.util.ValidationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CorrectContentStructureFileValidator implements ImportingFileValidator {

    @Override
    public boolean isValid(MultipartFile multipartFile) {
        return hasValidStructure(multipartFile);
    }

    private boolean hasValidStructure(MultipartFile file) {
        String content = ValidationUtils.extractContentFromFile(file);

        if (StringUtils.isBlank(content)) {
            return false;
        }

        String[] splitContent = content.split("\n");

        boolean isUUIDPartValid = hasUUIDPartInCorrectPlace(splitContent);
        boolean isTitlePartValid = hasTitlePartInCorrectPlace(splitContent);
        boolean isTypePartValid = hasTypePartInCorrectPlace(splitContent);
        boolean isDateOfCreationPartValid = hasDateOfCreationPartInCorrectPlace(splitContent);
        boolean isDateOfModificationPartValid = hasDateOfModificationPartInCorrectPlace(splitContent);
        boolean isTagsPartValid = hasTagsPartInCorrectPlace(splitContent);
        boolean isContentPartValid = hasContentPartInCorrectPlace(splitContent);

        return isUUIDPartValid
                && isTitlePartValid
                && isTypePartValid
                && isDateOfCreationPartValid
                && isDateOfModificationPartValid
                && isTagsPartValid
                && isContentPartValid;
    }

    private boolean hasUUIDPartInCorrectPlace(String[] splitContent) {
        String expectedUUIDPart = splitContent[0].trim();
        return expectedUUIDPart.startsWith("UUID: ");
    }

    private boolean hasTitlePartInCorrectPlace(String[] splitContent) {
        String expectedTitlePart = splitContent[1].trim();
        return expectedTitlePart.startsWith("Title: ");
    }

    private boolean hasTypePartInCorrectPlace(String[] splitContent) {
        String expectedTypePart = splitContent[2].trim();
        return expectedTypePart.startsWith("Type: ");
    }

    private boolean hasDateOfCreationPartInCorrectPlace(String[] splitContent) {
        String expectedDatePart = splitContent[3].trim();
        return expectedDatePart.startsWith("Date of creation: ");
    }

    private boolean hasDateOfModificationPartInCorrectPlace(String[] splitContent) {
        String expectedDatePart = splitContent[4].trim();
        return expectedDatePart.startsWith("Date of modification:");
    }

    private boolean hasTagsPartInCorrectPlace(String[] splitContent) {
        String expectedTagsPart = splitContent[5].trim();
        return expectedTagsPart.startsWith("Tags: ");
    }

    private boolean hasContentPartInCorrectPlace(String[] splitContent) {
        String expectedContentPart = splitContent[7].trim();
        return expectedContentPart.startsWith("Content:");
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
                
                (EID: 202504101400)
                """;
    }
}
