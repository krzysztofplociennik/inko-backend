package com.plociennik.common.errorhandling.exceptions;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

public class InkoImportException extends RuntimeException {

    @Getter
    private MultipartFileData fileData;

    public InkoImportException(String message, MultipartFile file) {
        super(message);
        this.fileData = MultipartFileData.fromMultipartFile(file);
    }
}
