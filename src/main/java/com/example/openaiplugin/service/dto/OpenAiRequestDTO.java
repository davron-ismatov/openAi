package com.example.openaiplugin.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OpenAiRequestDTO {
    private String model;
    private String prompt;
    private Integer maxTokens;
}
