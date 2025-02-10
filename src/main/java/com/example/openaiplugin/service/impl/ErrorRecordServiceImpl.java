package com.example.openaiplugin.service.impl;

import com.example.openaiplugin.domain.entity.ErrorRecord;
import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import com.example.openaiplugin.service.ErrorRecordService;
import com.example.openaiplugin.service.repository.ErrorRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ErrorRecordServiceImpl implements ErrorRecordService {
    private final ErrorRecordRepository repository;

    @Override
    public void saveErrorRecord(String error, String occurredClass, String occurredMethod, ResponseStatus responseStatus) {
        ErrorRecord errorRecord = generateErrorRecord(error,occurredClass,occurredMethod,responseStatus);

        log.debug("Saving occurred error: {}", errorRecord.getError());
        repository.save(errorRecord);
    }

    private ErrorRecord generateErrorRecord(String error, String occurredClass, String occurredMethod, ResponseStatus responseStatus) {
        return ErrorRecord.builder()
                .error(error)
                .service(occurredClass)
                .thrownMethod(occurredMethod)
                .responseStatus(responseStatus)
                .build();
    }
}
