package com.example.openaiplugin.service.impl;

import com.example.openaiplugin.config.props.OpenApiProperties;
import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import com.example.openaiplugin.service.OpenAIService;
import com.example.openaiplugin.service.dto.BaseResponse;
import com.example.openaiplugin.service.dto.OpenAIRequest;
import com.example.openaiplugin.service.dto.OpenAIResponse;
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

@Slf4j
@Service
@EnableConfigurationProperties(OpenApiProperties.class)
@ConditionalOnProperty(prefix = "open-ai", name = "simulate", havingValue = "true")
public class OpenAIServiceImpl implements OpenAIService {
    private final OpenApiProperties properties;
    private final RestTemplate restTemplate;
    private final String V1_COMPLETIONS = "/v1/completions";

    public OpenAIServiceImpl(OpenApiProperties properties, RestTemplate restTemplate) {
        log.debug("###OpenAIServiceImpl is started###");
        this.properties = properties;
        this.restTemplate = restTemplate;
    }

    @Override
    public BaseResponse<OpenAIResponse> sendOpenAI(OpenAIRequest openAIRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(properties.getKey());
        HttpEntity<OpenAIRequest> entity = new HttpEntity<>(openAIRequest, headers);

        try {

            var response = restTemplate.exchange(properties.getBaseUrl() + V1_COMPLETIONS, HttpMethod.POST, entity, new ParameterizedTypeReference<OpenAIResponse>() {
            });

            log.debug("Response : {}", response.getBody());
            if (response.getStatusCode().is2xxSuccessful())
                return new BaseResponse<>(response.getBody(), ResponseStatus.SUCCESS);

        } catch (HttpClientErrorException clientErrorException) {
            return new BaseResponse<>(null, ResponseStatus.CLIENT_ERROR, clientErrorException.getResponseBodyAsString());
        } catch (HttpServerErrorException serverErrorException) {
            return new BaseResponse<>(null, ResponseStatus.SERVER_ERROR, serverErrorException.getResponseBodyAsString());
        } catch (Exception e) {
            return new BaseResponse<>(null, ResponseStatus.UNKNOWN_ERROR, e.getMessage());
        }

        return new BaseResponse<>(null, ResponseStatus.UNKNOWN_ERROR);
    }
}
