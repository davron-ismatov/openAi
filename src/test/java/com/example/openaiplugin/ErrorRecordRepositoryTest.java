package com.example.openaiplugin;

import com.example.openaiplugin.domain.entity.ErrorRecord;
import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import com.example.openaiplugin.service.impl.OpenAiServiceImpl;
import com.example.openaiplugin.service.repository.ErrorRecordRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Testcontainers
public class ErrorRecordRepositoryTest {
    private static PostgreSQLContainer<?> postgres;
    private @Autowired ErrorRecordRepository errorRecordRepository;

    @BeforeAll
    public static void set_up() {
        postgres = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("openai_plugin")
                .withUsername("postgres")
                .withPassword("1234asdf");

        postgres.start();
    }

    @AfterAll
    public static void stop_containers() {
        postgres.stop();
    }

    @Test
    public void test_error_record_saving() {
        ErrorRecord errorRecord = generateErrorRecord();

        ErrorRecord saved = errorRecordRepository.save(errorRecord);

        assertNotNull(saved.getId());
        assertEquals("send",errorRecord.getThrownMethod());
    }

    private ErrorRecord generateErrorRecord() {
        return ErrorRecord.builder()
                .error(Exception.class.getName())
                .service(OpenAiServiceImpl.class.getName())
                .thrownMethod("send")
                .responseStatus(ResponseStatus.UNKNOWN_ERROR)
                .build();
    }
}
