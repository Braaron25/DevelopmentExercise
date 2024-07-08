package com.neoris.micro_one;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean
    public WebClient registrarWebClient() {
        return WebClient.create("http://localhost:8090/api/v1");
    }
}

