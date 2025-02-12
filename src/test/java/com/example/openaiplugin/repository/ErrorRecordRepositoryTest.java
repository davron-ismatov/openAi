package com.example.openaiplugin.repository;

import com.example.openaiplugin.domain.entity.ErrorRecord;
import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import com.example.openaiplugin.service.impl.OpenAiServiceImpl;
import com.example.openaiplugin.service.repository.ErrorRecordRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Testcontainers
public class ErrorRecordRepositoryTest {
    private @Autowired ErrorRecordRepository errorRecordRepository;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withReuse(true)
            .withUsername("postgres")
            .withPassword("postgres")
            .withDatabaseName("openai_plugin");

    @DynamicPropertySource
    static void datasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }


    @Test
    @Transactional
    public void test_error_record_saving() {
        ErrorRecord errorRecord = generateErrorRecord();

        ErrorRecord saved = errorRecordRepository.save(errorRecord);

        assertNotNull(saved.getId());
        assertEquals("send", errorRecord.getThrownMethod());
    }

    private ErrorRecord generateErrorRecord() {
        return ErrorRecord.builder()
                .error(Exception.class.getName())
                .service(OpenAiServiceImpl.class.getName())
                .thrownMethod("send")
                .responseStatus(ResponseStatus.UNKNOWN_ERROR)
                .description("testing")
                .build();
    }
}
