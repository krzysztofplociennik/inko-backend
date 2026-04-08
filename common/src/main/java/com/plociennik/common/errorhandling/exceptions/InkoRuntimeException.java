package com.plociennik.common.errorhandling.exceptions;

public class InkoRuntimeException extends RuntimeException {
    public InkoRuntimeException(String message, String eid) {
        super(message + eid);
    }
}
