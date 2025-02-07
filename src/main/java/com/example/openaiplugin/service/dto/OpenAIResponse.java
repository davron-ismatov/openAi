package com.example.openaiplugin.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class OpenAIResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("object")
    private String object;

    @JsonProperty("created")
    private long created;

    @JsonProperty("model")
    private String model;

    @JsonProperty("usage")
    private Usage usage;

    @JsonProperty("choices")
    private List<Choice> choices;

    @Getter
    @Builder
    @ToString
    public static class Usage {

        @JsonProperty("prompt_tokens")
        private int promptTokens;

        @JsonProperty("completion_tokens")
        private int completionTokens;

        @JsonProperty("total_tokens")
        private int totalTokens;

        @JsonProperty("completion_tokens_details")
        private CompletionTokensDetails completionTokensDetails;
    }

    @Getter
    @Builder
    @ToString
    public static class CompletionTokensDetails {

        @JsonProperty("reasoning_tokens")
        private int reasoningTokens;

        @JsonProperty("accepted_prediction_tokens")
        private int acceptedPredictionTokens;

        @JsonProperty("rejected_prediction_tokens")
        private int rejectedPredictionTokens;
    }


    @Getter
    @Builder
    @ToString
    public static class Choice {

        @JsonProperty("message")
        private Message message;

        @JsonProperty("logprobs")
        private Object logprobs;

        @JsonProperty("finish_reason")
        private String finishReason;

        @JsonProperty("index")
        private int index;
    }

    @Getter
    @Builder
    @ToString
    public static class Message {

        @JsonProperty("role")
        private String role;

        @JsonProperty("content")
        private String content;
    }
}
