package com.example.openaiplugin.service;

import com.example.openaiplugin.service.dto.OpenAiBaseResponse;
import com.example.openaiplugin.service.dto.OpenAiRequestDTO;
import com.example.openaiplugin.service.dto.OpenAiResponseDTO;

public interface OpenAiService {
    OpenAiBaseResponse<OpenAiResponseDTO> send(OpenAiRequestDTO openAIRequestDTO);
}
