package com.example.openaiplugin.controller;

import com.example.openaiplugin.service.OpenAiService;
import com.example.openaiplugin.service.dto.OpenAiBaseResponse;
import com.example.openaiplugin.service.dto.OpenAiRequestDTO;
import com.example.openaiplugin.service.dto.OpenAiResponseDTO;
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
    private final OpenAiService openAiService;

    @PostMapping("/completing")
    public OpenAiBaseResponse<OpenAiResponseDTO> completing(@RequestBody OpenAiRequestDTO openAiRequestDTO) {
        log.info("Sending OpenAI request: {}", openAiRequestDTO);
        return openAiService.send(openAiRequestDTO);
    }

}
