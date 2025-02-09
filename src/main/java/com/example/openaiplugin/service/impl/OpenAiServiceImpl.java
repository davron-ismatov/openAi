package com.example.openaiplugin.service.impl;

import com.example.openaiplugin.config.OpenAiProperties;
import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import com.example.openaiplugin.service.OpenAiService;
import com.example.openaiplugin.service.dto.BaseResponse;
import com.example.openaiplugin.service.dto.OpenAiRequest;
import com.example.openaiplugin.service.dto.OpenAiResponse;
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

    public OpenAiServiceImpl(OpenAiProperties properties, RestTemplate restTemplate) {
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
            return new BaseResponse<>(ResponseStatus.CLIENT_ERROR, clientErrorException.getResponseBodyAsString());
        } catch (HttpServerErrorException serverErrorException) {
            return new BaseResponse<>(ResponseStatus.SERVER_ERROR, serverErrorException.getResponseBodyAsString());
        } catch (Exception e) {
            return new BaseResponse<>(ResponseStatus.UNKNOWN_ERROR, e.getMessage());
        }
    }

}
