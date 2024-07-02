package com.plociennik.model;

public enum ArticleType {

    PROGRAMMING,
    TOOLS,
    OS;


    public static ArticleType getType(String s) {

        switch (s) {
            case null -> {
                return null;
            }
            case "PROGRAMMING" -> {
                return PROGRAMMING;
            }
            case "TOOLS" -> {
                return TOOLS;
            }
            case "OS" -> {
                return OS;
            }
            default -> throw new IllegalStateException("Unexpected value: " + s);
        }
    }
}
