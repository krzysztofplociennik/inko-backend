package com.plociennik.model;

import lombok.SneakyThrows;

public enum ArticleType {

    PROGRAMMING,
    TOOLS,
    DATABASE,
    OS;

    @SneakyThrows
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
            default -> throw new Exception("Unexpected value: " + s);
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
