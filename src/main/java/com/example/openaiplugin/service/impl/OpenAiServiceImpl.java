package com.example.openaiplugin.service.impl;

import com.example.openaiplugin.config.OpenAiProperties;
import com.example.openaiplugin.service.ErrorRecordService;
import com.example.openaiplugin.service.OpenAiService;
import com.example.openaiplugin.service.dto.BaseResponse;
import com.example.openaiplugin.service.dto.OpenAiRequestDTO;
import com.example.openaiplugin.service.dto.OpenAiResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.example.openaiplugin.utils.ResponseUtils.validateResponse;

@Slf4j
@Service
@EnableConfigurationProperties(OpenAiProperties.class)
@ConditionalOnProperty(prefix = "open-ai", name = "simulate", havingValue = "true")
public class OpenAiServiceImpl implements OpenAiService {
    private final OpenAiProperties properties;
    private final RestTemplate restTemplate;
    private final String OPEN_AI_COMPLETIONS_URI = "/v1/completions";

    public OpenAiServiceImpl(OpenAiProperties properties, RestTemplate restTemplate, ErrorRecordService errorRecordService) {
        log.debug("###OpenAiServiceImpl is started###");
        this.properties = properties;
        this.restTemplate = restTemplate;
    }

    @Override
    public BaseResponse<OpenAiResponseDTO> send(OpenAiRequestDTO openAiRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(properties.getSecretKey());
        HttpEntity<OpenAiRequestDTO> entity = new HttpEntity<>(openAiRequestDTO, headers);

        var response = restTemplate.exchange(properties.getBaseUrl() + OPEN_AI_COMPLETIONS_URI, HttpMethod.POST, entity, new ParameterizedTypeReference<OpenAiResponseDTO>() {
        });
        return validateResponse(response);
    }

}
