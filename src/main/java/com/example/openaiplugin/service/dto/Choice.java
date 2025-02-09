package com.example.openaiplugin.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Choice {
    private Message message;
    private Object logprobs;
    private String finishReason;
    private int index;
}
