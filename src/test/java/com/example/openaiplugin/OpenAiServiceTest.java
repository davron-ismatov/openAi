package com.example.openaiplugin;

import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import com.example.openaiplugin.service.OpenAiService;
import com.example.openaiplugin.service.dto.*;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@SpringBootTest(properties = {"open-ai.simulate=true"})
public class OpenAiServiceTest {

    @Autowired
    private OpenAiService openAiService;
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
    public void test_send_open_ai_for_success_response() throws JsonProcessingException {
        OpenAiResponseDTO mockResponse = generateMockResponse();

        mockServer.expect(once(), requestTo("https://api.openai.com/v1/completions"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(objectMapper.writeValueAsString(mockResponse), MediaType.APPLICATION_JSON));

        OpenAiRequestDTO request = generateMockRequest();
        BaseResponse<OpenAiResponseDTO> response = openAiService.send(request);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(ResponseStatus.SUCCESS);
        assertThat(response.getData().getChoices()).hasSize(1);
        assertThat(response.getData().getChoices().get(0).getMessage().getContent()).isEqualTo("Test");
    }

    @Test
    public void test_send_open_ai_for_null_response() throws JsonProcessingException {
        mockServer.expect(once(), requestTo("https://api.openai.com/v1/completions"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(objectMapper.writeValueAsString(null), MediaType.APPLICATION_JSON));

        OpenAiRequestDTO request = generateMockRequest();
        BaseResponse<OpenAiResponseDTO> response = openAiService.send(request);

        assertThat(response.getData()).isNull();
    }

    @Test
    public void test_send_open_ai_for_response_unknown_error() {
        OpenAiResponseDTO mockResponse = generateMockResponse();
        mockServer.expect(once(), requestTo("https://api.openai.com/v1/completions"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(mockResponse.toString(), MediaType.APPLICATION_JSON));

        OpenAiRequestDTO request = generateMockRequest();

        assertThrows(Exception.class, () -> {
            BaseResponse<OpenAiResponseDTO> response = openAiService.send(request);
            assertThat(response).isNotNull();
            assertThat(response.getStatus()).isEqualTo(ResponseStatus.UNKNOWN_ERROR);
        });
    }


    @Test
    public void test_send_open_ai_to_get_client_error_response() {
        mockServer.expect(once(), requestTo("https://api.openai.com/v1/completions"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        OpenAiRequestDTO request = generateMockRequest();

        assertThrows(HttpClientErrorException.class, () -> {
            BaseResponse<OpenAiResponseDTO> response = openAiService.send(request);
            assertThat(response).isNotNull();
            assertThat(response.getStatus()).isEqualTo(ResponseStatus.CLIENT_ERROR);
        });
    }


    @Test
    public void test_send_open_ai_to_get_server_error_response() {
        mockServer.expect(once(), requestTo("https://api.openai.com/v1/completions"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withServerError());

        OpenAiRequestDTO request = generateMockRequest();

        assertThrows(HttpServerErrorException.class, () -> {
            BaseResponse<OpenAiResponseDTO> response = openAiService.send(request);
            assertThat(response).isNotNull();
            assertThat(response.getStatus()).isEqualTo(ResponseStatus.SERVER_ERROR);
        });
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
                .model("3.5 turbo")
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
