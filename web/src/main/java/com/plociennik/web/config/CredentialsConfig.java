package com.plociennik.web.config;

import io.github.cdimascio.dotenv.Dotenv;

public class CredentialsConfig {

    private CredentialsConfig() {}

    public static void load() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DATABASE_URL", dotenv.get("DATABASE_URL"));
        System.setProperty("DB_USER", dotenv.get("DB_USER"));
        System.setProperty("DB_PASS", dotenv.get("DB_PASS"));
        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
    }
}
