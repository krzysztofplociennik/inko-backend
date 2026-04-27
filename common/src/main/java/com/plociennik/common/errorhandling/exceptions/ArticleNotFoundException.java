package com.plociennik.common.errorhandling.exceptions;

public class ArticleNotFoundException extends Exception {

    private static final String BASE_MESSAGE = "Article with ID [%s] has not been found! (eventID: %s)";

    public ArticleNotFoundException(String articleID, String eid) {
        super(BASE_MESSAGE.formatted(articleID, eid));
    }
}
