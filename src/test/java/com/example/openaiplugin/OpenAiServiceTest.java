package com.example.openaiplugin;

import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import com.example.openaiplugin.service.OpenAIService;
import com.example.openaiplugin.service.dto.BaseResponse;
import com.example.openaiplugin.service.dto.OpenAIRequest;
import com.example.openaiplugin.service.dto.OpenAIResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest(properties = {"open-ai.simulate=true"})
public class OpenAiServiceTest {

    @Autowired
    private OpenAIService openAIService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setup() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @AfterEach
    public void verify() {
        mockServer.verify();
    }


    @Test
    public void testSendOpenAISuccess() throws JsonProcessingException {
        OpenAIResponse mockResponse = generateMockResponse();

        mockServer.expect(once(), requestTo("https://api.openai.com/v1/completions"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(objectMapper.writeValueAsString(mockResponse), MediaType.APPLICATION_JSON));

        OpenAIRequest request = generateMockRequest();
        BaseResponse<OpenAIResponse> response = openAIService.sendOpenAI(request);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(ResponseStatus.SUCCESS);
        assertThat(response.getData().getChoices()).hasSize(1);
        assertThat(response.getData().getChoices().get(0).getMessage().getContent()).isEqualTo("Test");
    }

    @Test
    public void testSendOpenAIUnknownError() {
        OpenAIResponse mockResponse = generateMockResponse();
        mockServer.expect(once(), requestTo("https://api.openai.com/v1/completions"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(mockResponse.toString(), MediaType.APPLICATION_JSON));

        OpenAIRequest request = generateMockRequest();
        BaseResponse<OpenAIResponse> response = openAIService.sendOpenAI(request);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(ResponseStatus.UNKNOWN_ERROR);
    }


    @Test
    public void testSendOpenAIClientError() throws JsonProcessingException {
        mockServer.expect(once(), requestTo("https://api.openai.com/v1/completions"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        OpenAIRequest request = generateMockRequest();
        BaseResponse<OpenAIResponse> response = openAIService.sendOpenAI(request);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(ResponseStatus.CLIENT_ERROR);
    }


    @Test
    public void testSendOpenAIServerError() throws JsonProcessingException {
        mockServer.expect(once(), requestTo("https://api.openai.com/v1/completions"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        OpenAIRequest request = generateMockRequest();
        BaseResponse<OpenAIResponse> response = openAIService.sendOpenAI(request);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(ResponseStatus.SERVER_ERROR);
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
                .model("3.5 turbo")
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
