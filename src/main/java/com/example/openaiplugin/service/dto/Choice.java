package com.example.openaiplugin.service.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Choice {
    private Message message;
    /**
     * It is logarithmic probability scores, it represents the answer accuracy of chatGPT model
     */
    private JsonNode logprobs;
    private String finishReason;
    private Integer index;
}
