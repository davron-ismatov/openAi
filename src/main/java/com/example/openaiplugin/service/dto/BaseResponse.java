package com.example.openaiplugin.service.dto;

import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BaseResponse<T> {
    private T data;
    private ResponseStatus status;
    private String message;

    public BaseResponse(T data, ResponseStatus status) {
        this.data = data;
        this.status = status;
    }

    public BaseResponse(T data) {
        this.data = data;
        this.status = ResponseStatus.SUCCESS;
    }

    public BaseResponse(ResponseStatus status) {
        this(null, status);
        this.message = status.getValue();
    }
}
