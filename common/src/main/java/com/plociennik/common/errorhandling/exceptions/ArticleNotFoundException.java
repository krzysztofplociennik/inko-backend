package com.plociennik.common.errorhandling.exceptions;

public class ArticleNotFoundException extends Exception {

    private static final String BASE_MESSAGE = "[EID: %s] Article with ID [%s] has not been found!";

    public ArticleNotFoundException(String eid, String articleID) {
        super(BASE_MESSAGE.formatted(eid, articleID));
    }
}
