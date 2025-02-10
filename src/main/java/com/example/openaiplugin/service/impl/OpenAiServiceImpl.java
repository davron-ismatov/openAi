package com.example.openaiplugin.service.impl;

import com.example.openaiplugin.config.OpenAiProperties;
import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import com.example.openaiplugin.service.ErrorRecordService;
import com.example.openaiplugin.service.OpenAiService;
import com.example.openaiplugin.service.dto.BaseResponse;
import com.example.openaiplugin.service.dto.OpenAiRequest;
import com.example.openaiplugin.service.dto.OpenAiResponse;
import com.example.openaiplugin.service.repository.ErrorRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static com.example.openaiplugin.utils.ResponseUtils.validateResponse;

@Slf4j
@Service
@EnableConfigurationProperties(OpenAiProperties.class)
@ConditionalOnProperty(prefix = "open-ai", name = "simulate", havingValue = "true")
public class OpenAiServiceImpl implements OpenAiService {
    private final OpenAiProperties properties;
    private final RestTemplate restTemplate;
    private final String OPEN_AI_COMPLETIONS = "/v1/completions";
    private final ErrorRecordService errorRecordService;

    public OpenAiServiceImpl(OpenAiProperties properties, RestTemplate restTemplate, ErrorRecordService errorRecordService) {
        this.errorRecordService = errorRecordService;
        log.debug("###OpenAiServiceImpl is started###");
        this.properties = properties;
        this.restTemplate = restTemplate;
    }

    @Override
    public BaseResponse<OpenAiResponse> send(OpenAiRequest openAIRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(properties.getKey());
        HttpEntity<OpenAiRequest> entity = new HttpEntity<>(openAIRequest, headers);

        try {
            var response = restTemplate.exchange(properties.getBaseUrl() + OPEN_AI_COMPLETIONS, HttpMethod.POST, entity, new ParameterizedTypeReference<OpenAiResponse>() {
            });

            return validateResponse(response);
        } catch (HttpClientErrorException clientErrorException) {
            errorRecordService.saveErrorRecord(
                    clientErrorException.getClass().getName(), OpenAiServiceImpl.class.getName(), "send",ResponseStatus.CLIENT_ERROR
            );

            return new BaseResponse<>(ResponseStatus.CLIENT_ERROR, clientErrorException.getResponseBodyAsString());
        } catch (HttpServerErrorException serverErrorException) {
            errorRecordService.saveErrorRecord(
                    serverErrorException.getClass().getName(), OpenAiServiceImpl.class.getName(), "send",ResponseStatus.SERVER_ERROR
            );

            return new BaseResponse<>(ResponseStatus.SERVER_ERROR, serverErrorException.getResponseBodyAsString());
        } catch (Exception e) {
            errorRecordService.saveErrorRecord(
                    e.getClass().getName(), OpenAiServiceImpl.class.getName(), "send",ResponseStatus.UNKNOWN_ERROR
            );

            return new BaseResponse<>(ResponseStatus.UNKNOWN_ERROR, e.getMessage());
        }
    }



}
