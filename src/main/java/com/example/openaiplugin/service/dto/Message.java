package com.example.openaiplugin.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Message {
    private String role;
    private String content;
}
