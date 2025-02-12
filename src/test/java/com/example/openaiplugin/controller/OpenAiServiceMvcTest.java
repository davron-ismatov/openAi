package com.example.openaiplugin.controller;

import com.example.openaiplugin.config.TestConfig;
import com.example.openaiplugin.service.OpenAiService;
import com.example.openaiplugin.service.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestConfig.class)
@WebMvcTest(OpenAiController.class)
public class OpenAiServiceMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private OpenAiService openAiService;

    @Test
    public void test_for_open_ai_completing_api_checking_controller_behaviour() throws Exception {
        OpenAiRequestDTO openAiRequestDTO = generateMockRequest();
        OpenAiResponseDTO openAiResponseDTO = generateMockResponse();

        when(openAiService.send(openAiRequestDTO)).thenReturn(new OpenAiBaseResponse<>(openAiResponseDTO));

        String request = objectMapper.writeValueAsString(openAiRequestDTO);
        String expectedResponse = objectMapper.writeValueAsString(new OpenAiBaseResponse<>(openAiResponseDTO));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/open-ai/completing")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

    private OpenAiRequestDTO generateMockRequest() {
        OpenAiRequestDTO request = new OpenAiRequestDTO();
        request.setPrompt("test");
        request.setModel("gpt-3.5-turbo");
        request.setMaxTokens(4);
        return request;
    }

    private OpenAiResponseDTO generateMockResponse() {
        return OpenAiResponseDTO.builder()
                .id("cmpl-6dTsk4HDk4fjZy1QRXgA7MbL0Ljx5")
                .object("text_completion")
                .created(1677826800L)
                .model("gpt-3.5-turbo")
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
