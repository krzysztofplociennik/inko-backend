package com.plociennik.service.importing.validation;

import org.springframework.web.multipart.MultipartFile;

public interface ImportingFileValidator extends ImportValidator {
    boolean isValid(MultipartFile multipartFile);
}
