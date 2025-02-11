package com.example.openaiplugin.service.impl;

import com.example.openaiplugin.domain.entity.ErrorRecord;
import com.example.openaiplugin.service.mapper.ErrorRecordMapper;
import com.example.openaiplugin.service.ErrorRecordService;
import com.example.openaiplugin.service.dto.ErrorRecordDTO;
import com.example.openaiplugin.service.repository.ErrorRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ErrorRecordServiceImpl implements ErrorRecordService {
    private final ErrorRecordRepository repository;
    private final ErrorRecordMapper mapper;

    @Override
    public void saveErrorRecord(ErrorRecordDTO dto) {
        ErrorRecord errorRecord = mapper.toEntity(dto);

        log.debug("Saving occurred error: {}", errorRecord.getError());
        repository.save(errorRecord);
    }
}
