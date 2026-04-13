package com.plociennik.common.errorhandling.exceptions;

public class ArticleNotFoundException extends Exception {

    private final static String BASE_MESSAGE = "Article with ID [{}] has not been found! ";

    public ArticleNotFoundException(String articleID, String eid) {
        super(BASE_MESSAGE.replace("{}", articleID) + eid);
    }
}
