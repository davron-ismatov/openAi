package com.example.openaiplugin.service;

import com.example.openaiplugin.service.dto.BaseResponse;
import com.example.openaiplugin.service.dto.OpenAiRequestDTO;
import com.example.openaiplugin.service.dto.OpenAiResponseDTO;

public interface OpenAiService {
    BaseResponse<OpenAiResponseDTO> send(OpenAiRequestDTO openAIRequestDTO);
}
