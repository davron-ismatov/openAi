package com.example.openaiplugin.config;

import com.example.openaiplugin.service.OpenAiService;
import com.example.openaiplugin.service.impl.SimOpenAiServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean("mockOpenAiService")
    public OpenAiService openAiService() {
        return new SimOpenAiServiceImpl();
    }
}
