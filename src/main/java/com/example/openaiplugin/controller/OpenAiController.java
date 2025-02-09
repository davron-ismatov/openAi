package com.example.openaiplugin.controller;

import com.example.openaiplugin.service.OpenAiService;
import com.example.openaiplugin.service.dto.BaseResponse;
import com.example.openaiplugin.service.dto.OpenAiRequest;
import com.example.openaiplugin.service.dto.OpenAiResponse;
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
public class OpenAiController {
    private final OpenAiService openAIService;

    @PostMapping("/completing")
    public BaseResponse<OpenAiResponse> completing(@RequestBody OpenAiRequest openAIRequest) {
        log.info("Sending OpenAI request: {}", openAIRequest);
        return openAIService.send(openAIRequest);
    }

}
