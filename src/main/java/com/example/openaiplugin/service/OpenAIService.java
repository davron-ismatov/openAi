package com.example.openaiplugin.service;

import com.example.openaiplugin.service.dto.BaseResponse;
import com.example.openaiplugin.service.dto.OpenAIRequest;
import com.example.openaiplugin.service.dto.OpenAIResponse;

public interface OpenAIService {
    BaseResponse<OpenAIResponse> sendOpenAI(OpenAIRequest openAIRequest);
}
