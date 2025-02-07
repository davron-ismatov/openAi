package com.example.openaiplugin;

import com.example.openaiplugin.controller.OpenAIController;
import com.example.openaiplugin.service.OpenAIService;
import com.example.openaiplugin.service.dto.BaseResponse;
import com.example.openaiplugin.service.dto.OpenAIRequest;
import com.example.openaiplugin.service.dto.OpenAIResponse;
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
@WebMvcTest(OpenAIController.class)
public class OpenAIServiceMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private OpenAIService openAIService;


    @Test
    public void testCompleting() throws Exception {
        OpenAIRequest openAIRequest = generateMockRequest();
        OpenAIResponse openAIResponse = generateMockResponse();

        when(openAIService.sendOpenAI(openAIRequest)).thenReturn(new BaseResponse<>(openAIResponse));


        String request = objectMapper.writeValueAsString(openAIRequest);
        String expectedResponse = objectMapper.writeValueAsString(new BaseResponse<>(openAIResponse));


        mockMvc.perform(MockMvcRequestBuilders.post("/api/open-ai/completing")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

    }


    private OpenAIRequest generateMockRequest() {
        OpenAIRequest request = new OpenAIRequest();
        request.setPrompt("test");
        request.setModel("gpt-3.5-turbo");
        request.setMaxTokens(4);
        return request;
    }

    private OpenAIResponse generateMockResponse() {
        return OpenAIResponse.builder()
                .id("cmpl-6dTsk4HDk4fjZy1QRXgA7MbL0Ljx5")
                .object("text_completion")
                .created(1677826800L)
                .model("gpt-3.5-turbo")
                .choices(List.of(
                        OpenAIResponse.Choice.builder()
                                .message(OpenAIResponse.Message.builder()
                                        .content("Test")
                                        .build())
                                .index(0)
                                .build()
                ))
                .build();
    }
}
