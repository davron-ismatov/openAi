package com.example.openaiplugin.config.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "open-ai")
@Getter
@Setter
public class OpenApiProperties {
    private String baseUrl;
    private String key;
}
