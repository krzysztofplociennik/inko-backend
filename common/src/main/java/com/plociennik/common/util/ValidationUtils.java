package com.plociennik.common.util;

import com.plociennik.common.errorhandling.exceptions.InkoRuntimeException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ValidationUtils {

    private ValidationUtils() {}

    public static String extractContentFromFile(MultipartFile file) {
        String content = null;
        try {
            content = new String(file.getBytes());
        } catch (IOException e) {
            throw new InkoRuntimeException(e.getMessage(), "202504031243");
        }
        return content;
    }
}
