package com.example.openaiplugin.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseStatus {
    SUCCESS(200, new Message("Muvaffaqiyatli", "Успешно", "Success")),
    RESPONSE_IS_NULL(-1000, new Message("Javob bo'sh", "Ответ пуст", "Response is null")),
    CLIENT_ERROR(-1001, new Message("Mijoz xatosi", "Ошибка клиента", "Client error")),
    SERVER_ERROR(-1002, new Message("Server xatosi", "Ошибка сервера", "Server error")),
    UNKNOWN_ERROR(-1003, new Message("Noma'lum xato", "Неизвестная ошибка", "Unknown error")),
    RESPONSE_BODY_IS_NULL(-1004, new Message("Kutilayotgan javob bo'sh", "Тело ответа пусто", "Response body is null"));

    private final Integer code;
    private final Message message;

    public record Message(String messageUz, String messageRu, String messageEn) {
    }
}
