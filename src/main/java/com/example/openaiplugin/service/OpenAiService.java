package com.example.openaiplugin.service;

import com.example.openaiplugin.service.dto.BaseResponse;
import com.example.openaiplugin.service.dto.OpenAiRequest;
import com.example.openaiplugin.service.dto.OpenAiResponse;

public interface OpenAiService {
    BaseResponse<OpenAiResponse> send(OpenAiRequest openAIRequest);
}
