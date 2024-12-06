package com.plociennik.common;

public class ArticleNotFoundException extends Exception {

    private final static String BASE_MESSAGE = "Article with ID [{}] has not been found! ";
    private final static String EID = "(eid: 061220240913)";

    public ArticleNotFoundException(String articleID) {
        super(BASE_MESSAGE.replace("{}", articleID) + EID);
    }
}
