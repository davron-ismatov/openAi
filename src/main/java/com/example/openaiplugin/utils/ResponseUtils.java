package com.example.openaiplugin.utils;

import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import com.example.openaiplugin.service.dto.OpenAiBaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Slf4j
public class ResponseUtils {

    public static <T> OpenAiBaseResponse<T> validateResponse(ResponseEntity<T> responseEntity) {
        if (responseEntity == null) {
            log.debug("Response is null");
            return new OpenAiBaseResponse<>(ResponseStatus.RESPONSE_IS_NULL);
        }

        if (responseEntity.getBody() == null) {
            log.debug("Response body is null");
            return new OpenAiBaseResponse<>(ResponseStatus.RESPONSE_BODY_IS_NULL);
        }

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Response : {}", responseEntity.getBody());
            return new OpenAiBaseResponse<>(responseEntity.getBody(), ResponseStatus.SUCCESS);
        }

        if (responseEntity.getStatusCode().is5xxServerError()) {
            log.debug("Internal server error occurred: {}", responseEntity.getBody());
            throw new HttpServerErrorException(responseEntity.getStatusCode());
        }

        if (responseEntity.getStatusCode().is4xxClientError()) {
            log.debug("Client server error occurred: {}", responseEntity.getBody());
            throw new HttpClientErrorException(responseEntity.getStatusCode());
        }

        throw new RuntimeException();
    }
}
