package com.plociennik.common.errorhandling;

import com.plociennik.common.errorhandling.exceptions.InkoImportException;
import com.plociennik.common.errorhandling.exceptions.InkoValidationException;
import com.plociennik.common.errorhandling.responses.ErrorResponse;
import com.plociennik.common.errorhandling.exceptions.LoginCredentialsInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginCredentialsInvalidException.class)
    public ResponseEntity<ErrorResponse> handleLoginCredentialsInvalidException(LoginCredentialsInvalidException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(InkoValidationException.class)
    public ResponseEntity<String> handleValidationException(InkoValidationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(InkoImportException.class)
    public ResponseEntity<String> handleInkoImportException(InkoImportException ex) {
        String message = ex.getMessage() + "\n\nFile data:\n\n" + ex.getFileData().toString();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(message);
    }
}
