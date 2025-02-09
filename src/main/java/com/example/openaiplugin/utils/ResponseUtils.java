package com.example.openaiplugin.utils;

import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import com.example.openaiplugin.service.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ResponseUtils {

    public static <T> BaseResponse<T> validateResponse(ResponseEntity<T> responseEntity) {
        if (responseEntity==null){
            log.debug("Response is null");
            return new BaseResponse<>(ResponseStatus.RESPONSE_IS_NULL);
        }

        if (responseEntity.getBody()==null){
            log.debug("Response body is null");
            return new BaseResponse<>(ResponseStatus.RESPONSE_BODY_IS_NULL);
        }

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.debug("Response : {}", responseEntity.getBody());
            return new BaseResponse<>(responseEntity.getBody(), ResponseStatus.SUCCESS);
        }

        return new BaseResponse<>(ResponseStatus.UNKNOWN_ERROR);
    }

}
