package com.example.openaiplugin.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenAIRequest {
    private String model;
    private String prompt;
    private int maxTokens;
}
