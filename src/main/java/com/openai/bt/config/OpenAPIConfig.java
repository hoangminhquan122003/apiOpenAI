package com.openai.bt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class OpenAPIConfig {
    @Value("${openai.api.url}")
    private String url;

    @Bean
    public RestClient restClient(){
        return RestClient.builder().baseUrl(url)
                .build();
    }
}
