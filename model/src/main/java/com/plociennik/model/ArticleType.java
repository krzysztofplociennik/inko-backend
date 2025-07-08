package com.plociennik.model;

import com.plociennik.common.errorhandling.exceptions.InkoRuntimeException;

public enum ArticleType {

    PROGRAMMING,
    TOOLS,
    DATABASE,
    OS;

    public static ArticleType getType(String s) {

        switch (s) {
            case "Programming" -> {
                return PROGRAMMING;
            }
            case "Tools" -> {
                return TOOLS;
            }
            case "Database" -> {
                return DATABASE;
            }
            case "OS" -> {
                return OS;
            }
            default -> throw new InkoRuntimeException("Unexpected value: [" + s + "]", "202504090950");
        }
    }

    @Override
    public String toString() {
        if (name().equals("OS")) {
            return OS.name();
        }
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
