package com.example.openaiplugin.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OpenAiLoggingUtils {
    public static String AUTHORIZATION_PATTERN = "Authorization:\\s*\"?Bearer\\s*([^\\s\"]+)\"?";
    public static String TOKEN_PATTERN = "(?i)token\\s*=\\s*\"?([^\\s\"]+)\"?";
    public static String USERNAME_PATTERN = "(?i)username\\s*=\\s*\"?([^\\s\"]+)\"?";
    public static String PASSWORD_PATTERN = "(?i)password\\s*=\\s*\"?([^\\s\"]+)\"?";
    public static String CREDIT_CARD_PATTERN = "(?i)(?:\\d{4}[ -]?){3}\\d{4}";
}
