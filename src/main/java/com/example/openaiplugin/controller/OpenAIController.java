package com.example.openaiplugin.controller;

import com.example.openaiplugin.service.OpenAIService;
import com.example.openaiplugin.service.dto.BaseResponse;
import com.example.openaiplugin.service.dto.OpenAIRequest;
import com.example.openaiplugin.service.dto.OpenAIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/open-ai")
public class OpenAIController{
    private final OpenAIService openAIService;

    @PostMapping("/completing")
    public BaseResponse<OpenAIResponse> completing(@RequestBody OpenAIRequest openAIRequest) {
        log.info("Sending OpenAI request: {}", openAIRequest);
        return openAIService.sendOpenAI(openAIRequest);
    }



}
