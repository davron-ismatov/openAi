package com.example.openaiplugin.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Usage {
    private Integer promptTokens;
    private Integer completionTokens;
    private Integer totalTokens;
    private CompletionTokensDetails completionTokensDetails;
}
