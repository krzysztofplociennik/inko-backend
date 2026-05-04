package com.plociennik.common.errorhandling.exceptions;

public class LoginCredentialsInvalidException extends Exception {

    private final static String BASE_MESSAGE = "[EID: %s] User with ID provided credentials has not been found!";

    public LoginCredentialsInvalidException(String eid) {
        super(BASE_MESSAGE.formatted(eid));
    }
}
