package com.example.openaiplugin.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenAiRequest {
    private String model;
    private String prompt;
    private Integer maxTokens;
}
