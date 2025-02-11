package com.example.openaiplugin.handler;

import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import com.example.openaiplugin.service.dto.BaseResponse;
import com.example.openaiplugin.service.dto.OpenAiResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    public GlobalExceptionHandler() {
        log.info("###GlobalExceptionHandler instantiated###");
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public BaseResponse<OpenAiResponseDTO> handleHttpClientErrorException(HttpClientErrorException e) {
        log.info("Exception handled by HttpClientErrorException");
        return new BaseResponse<>(ResponseStatus.CLIENT_ERROR, e.getMessage());
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public BaseResponse<OpenAiResponseDTO> handleHttpServerErrorException(HttpServerErrorException e) {
        log.info("Exception handled by HttpServerErrorException");
        return new BaseResponse<>(ResponseStatus.SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse<OpenAiResponseDTO> handleException(Exception e) {
        log.info("Exception handled by Exception");
        return new BaseResponse<>(ResponseStatus.UNKNOWN_ERROR, e.getMessage());
    }

}
