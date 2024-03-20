package com.plociennik.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.plociennik.model.repository")
@Configuration
@EntityScan("com.plociennik.model")
public class Config {
}
