package com.example.openaiplugin.utils;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import static com.example.openaiplugin.utils.OpenAiLoggingUtils.*;

public class OpenAiLoggingConverter extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        String formattedMessage = iLoggingEvent.getFormattedMessage();

        if (formattedMessage != null) {
            formattedMessage = formattedMessage.replaceAll(AUTHORIZATION_PATTERN, "Authorization: [REDACTED]");
            formattedMessage = formattedMessage.replaceAll(TOKEN_PATTERN, "Token: [REDACTED]");
            formattedMessage = formattedMessage.replaceAll(USERNAME_PATTERN, "Username: [REDACTED]");
            formattedMessage = formattedMessage.replaceAll(PASSWORD_PATTERN, "Username: [REDACTED]");
            formattedMessage = formattedMessage.replaceAll(CREDIT_CARD_PATTERN, "Username: [REDACTED]");
        }

        return formattedMessage;
    }
}
