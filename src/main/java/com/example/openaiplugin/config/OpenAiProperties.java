package com.example.openaiplugin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "open-ai")
public class OpenAiProperties {
    private String baseUrl;
    private String secretKey;
    private Integer connectionTimeout;
    private Boolean simulate;
    private Integer readTimeout;
}
