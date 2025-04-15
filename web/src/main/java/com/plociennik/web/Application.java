package com.plociennik.web;

import com.plociennik.web.config.CredentialsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(
        basePackages = {
                "com.plociennik.common",
                "com.plociennik.model",
                "com.plociennik.service",
                "com.plociennik.web",
        }
)
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        CredentialsConfig.load();
        SpringApplication.run(Application.class, args);
    }
}