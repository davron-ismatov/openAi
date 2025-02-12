package com.example.openaiplugin.service.aspect;

import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import com.example.openaiplugin.service.ErrorRecordService;
import com.example.openaiplugin.service.dto.ErrorRecordDTO;
import com.example.openaiplugin.service.dto.OpenAiBaseResponse;
import com.example.openaiplugin.service.dto.OpenAiResponseDTO;
import com.example.openaiplugin.service.impl.OpenAiServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ExceptionLogger {
    private final ErrorRecordService errorRecordService;

    @AfterThrowing(
            value = "execution(public com.example.openaiplugin.service.dto.OpenAiBaseResponse<com.example.openaiplugin.service.dto.OpenAiResponseDTO> com.example.openaiplugin.service.impl.OpenAiServiceImpl.send(com.example.openaiplugin.service.dto.OpenAiRequestDTO))",
            throwing = "ex"
    )
    public OpenAiBaseResponse<OpenAiResponseDTO> afterThrowing(Exception ex) {
        ErrorRecordDTO dto = ErrorRecordDTO.builder()
                .description(ex.getMessage())
                .thrownMethod("send")
                .service(OpenAiServiceImpl.class.getName())
                .build();

        log.info("Exception occurred -> {}", ex.getMessage());

        if (ex instanceof HttpClientErrorException clientErrorException) {
            dto.setResponseStatus(ResponseStatus.CLIENT_ERROR);
            dto.setError(clientErrorException.getClass().getName());
            throw clientErrorException;
        } else if (ex instanceof HttpServerErrorException serverErrorException) {
            dto.setResponseStatus(ResponseStatus.SERVER_ERROR);
            dto.setError(serverErrorException.getClass().getName());
        } else if (ex instanceof RuntimeException) {
            dto.setResponseStatus(ResponseStatus.UNKNOWN_ERROR);
            dto.setError(ex.getClass().getName());
        }

        dto.setDescription(ex.getMessage());
        log.info("Handled Exception dto: {}", dto);
        errorRecordService.saveErrorRecord(dto);

        return new OpenAiBaseResponse<>(dto.getResponseStatus(), ex.getMessage());
    }
}
