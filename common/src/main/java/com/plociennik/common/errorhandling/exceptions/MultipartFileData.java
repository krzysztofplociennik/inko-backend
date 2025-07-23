package com.plociennik.common.errorhandling.exceptions;

import lombok.Builder;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Builder
@ToString
public class MultipartFileData {

    private String filename;
    private long filesize;
    private String contentType;
    private String rawContent;

    public static MultipartFileData fromMultipartFile(MultipartFile file) {
        return MultipartFileData.builder()
                .filename(file.getOriginalFilename())
                .filesize(file.getSize())
                .contentType(file.getContentType())
                .build();
    }
}
