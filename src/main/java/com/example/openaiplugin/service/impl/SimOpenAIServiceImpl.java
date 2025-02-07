package com.example.openaiplugin.service.impl;

import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import com.example.openaiplugin.service.OpenAIService;
import com.example.openaiplugin.service.dto.BaseResponse;
import com.example.openaiplugin.service.dto.OpenAIRequest;
import com.example.openaiplugin.service.dto.OpenAIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@ConditionalOnProperty(prefix = "open-ai", name = "simulate", havingValue = "false")
public class SimOpenAIServiceImpl implements OpenAIService {

    public SimOpenAIServiceImpl() {
        log.debug("###SimOpenAIServiceImpl is started###");
    }

    @Override
    public BaseResponse<OpenAIResponse> sendOpenAI(OpenAIRequest openAIRequest) {
        OpenAIResponse openAIResponse = generateMockResponse(openAIRequest);

        log.debug("Generated mock response -> {}", openAIResponse);

        return new BaseResponse<>(openAIResponse, ResponseStatus.SUCCESS);
    }

    private OpenAIResponse generateMockResponse(OpenAIRequest openAIRequest) {
        return OpenAIResponse.builder()
                .id("cmpl-6dTsk4HDk4fjZy1QRXgA7MbL0Ljx5")
                .object("text_completion")
                .created(1677826800L)
                .model(openAIRequest.getModel())
                .choices(List.of(
                        OpenAIResponse.Choice.builder()
                                .message(OpenAIResponse.Message.builder()
                                        .content("Test")
                                        .build())
                                .index(0)
                                .build()
                ))
                .build();
    }

}
