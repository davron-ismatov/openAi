package com.example.openaiplugin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(OpenAiProperties.class)
public class OpenAiClientConfig {
    private final OpenAiProperties openAiProperties;

    @Bean(name = "openAiRestTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .messageConverters(createMessageConverters())
                .build();
    }

    private List<HttpMessageConverter<?>> createMessageConverters() {
        MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
        return List.of(jsonMessageConverter);
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(openAiProperties.getConnectionTimeout());
        factory.setReadTimeout(openAiProperties.getReadTimeout());
        return factory;
    }
}
