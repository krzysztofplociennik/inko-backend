package com.plociennik.service.importing.validation;

import com.plociennik.service.importing.dto.ImportFilesRequestBody;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class MissingFilesFileRequestValidator implements ImportFilesRequestValidator {

    @Override
    public boolean isValid(ImportFilesRequestBody requestBody) {

        List<MultipartFile> files = requestBody.getFiles();
        if (files == null || files.isEmpty()) {
            return false;
        }

        for (MultipartFile file : files) {
            if (file == null) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String createValidationFailureMessage() {
        return "The files or one of the files in the request body are missing! (EID: 202504021038)";
    }
}
