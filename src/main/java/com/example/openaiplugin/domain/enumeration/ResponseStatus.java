package com.example.openaiplugin.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseStatus {
    SUCCESS("SUCCESS"),
    RESPONSE_IS_NULL("RESPONSE_IS_NULL"),
    CLIENT_ERROR("CLIENT_ERROR"),
    SERVER_ERROR("SERVER_ERROR"),
    UNKNOWN_ERROR("UNKNOWN_ERROR");

    private final String value;
}
