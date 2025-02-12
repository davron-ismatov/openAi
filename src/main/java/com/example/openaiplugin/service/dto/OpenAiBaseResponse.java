package com.example.openaiplugin.service.dto;

import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OpenAiBaseResponse<T> {
    private T data;
    private ResponseStatus status;
    private String message;

    public OpenAiBaseResponse(T data, ResponseStatus status) {
        this.data = data;
        this.status = status;
    }

    public OpenAiBaseResponse(T data) {
        this.data = data;
        this.status = ResponseStatus.SUCCESS;
    }

    public OpenAiBaseResponse(ResponseStatus status, String message) {
        this(null, status);
        this.message = message;
    }

    public OpenAiBaseResponse(ResponseStatus status) {
        this(null, status);
        this.message = status.getMessage().messageUz() + "\n" + status.getMessage().messageRu() + "\n" + status.getMessage().messageEn();
    }
}
