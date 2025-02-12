package com.example.openaiplugin.handler;

import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import com.example.openaiplugin.service.dto.OpenAiBaseResponse;
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
    public OpenAiBaseResponse<OpenAiResponseDTO> handleHttpClientErrorException(HttpClientErrorException e) {
        log.info("Exception handled by HttpClientErrorException");
        return new OpenAiBaseResponse<>(ResponseStatus.CLIENT_ERROR, e.getMessage());
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public OpenAiBaseResponse<OpenAiResponseDTO> handleHttpServerErrorException(HttpServerErrorException e) {
        log.info("Exception handled by HttpServerErrorException");
        return new OpenAiBaseResponse<>(ResponseStatus.SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public OpenAiBaseResponse<OpenAiResponseDTO> handleException(Exception e) {
        log.info("Exception handled by Exception");
        return new OpenAiBaseResponse<>(ResponseStatus.UNKNOWN_ERROR, e.getMessage());
    }
}
