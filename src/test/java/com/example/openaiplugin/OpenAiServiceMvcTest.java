package com.example.openaiplugin;

import com.example.openaiplugin.controller.OpenAiController;
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
    private OpenAiService openAIService;

    @Test
    public void testCompleting() throws Exception {
        OpenAiRequest openAIRequest = generateMockRequest();
        OpenAiResponse openAIResponse = generateMockResponse();

        when(openAIService.send(openAIRequest)).thenReturn(new BaseResponse<>(openAIResponse));

        String request = objectMapper.writeValueAsString(openAIRequest);
        String expectedResponse = objectMapper.writeValueAsString(new BaseResponse<>(openAIResponse));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/open-ai/completing")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

    }

    private OpenAiRequest generateMockRequest() {
        OpenAiRequest request = new OpenAiRequest();
        request.setPrompt("test");
        request.setModel("gpt-3.5-turbo");
        request.setMaxTokens(4);
        return request;
    }

    private OpenAiResponse generateMockResponse() {
        return OpenAiResponse.builder()
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
