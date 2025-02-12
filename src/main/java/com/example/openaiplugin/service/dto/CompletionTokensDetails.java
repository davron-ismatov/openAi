package com.example.openaiplugin.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CompletionTokensDetails {
    private Integer reasoningTokens;
    private Integer acceptedPredictionTokens;
    private Integer rejectedPredictionTokens;
}
