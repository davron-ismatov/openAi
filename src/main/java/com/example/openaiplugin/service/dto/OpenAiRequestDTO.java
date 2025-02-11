package com.example.openaiplugin.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenAiRequestDTO {
    private String model;
    private String prompt;
    private Integer maxTokens;
}
