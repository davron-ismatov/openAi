package com.example.openaiplugin.service.impl;

import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import com.example.openaiplugin.service.OpenAiService;
import com.example.openaiplugin.service.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@ConditionalOnProperty(prefix = "open-ai", name = "simulate", havingValue = "false")
public class SimOpenAiServiceImpl implements OpenAiService {

    public SimOpenAiServiceImpl() {
        log.debug("###SimOpenAiServiceImpl is started###");
    }

    @Override
    public OpenAiBaseResponse<OpenAiResponseDTO> send(OpenAiRequestDTO openAIRequestDTO) {
        OpenAiResponseDTO openAIResponseDTO = generateMockResponse(openAIRequestDTO);

        log.debug("Generated mock response -> {}", openAIResponseDTO);

        return new OpenAiBaseResponse<>(openAIResponseDTO, ResponseStatus.SUCCESS);
    }

    private OpenAiResponseDTO generateMockResponse(OpenAiRequestDTO openAIRequestDTO) {
        return OpenAiResponseDTO.builder()
                .id("cmpl-6dTsk4HDk4fjZy1QRXgA7MbL0Ljx5")
                .object("text_completion")
                .created(1677826800L)
                .model(openAIRequestDTO.getModel())
                .choices(List.of(
                        Choice.builder()
                                .message(Message.builder()
                                        .content("Test")
                                        .build())
                                .index(0)
                                .build()
                ))
                .build();
    }

}
