package com.example.openaiplugin.service;

import com.example.openaiplugin.service.dto.ErrorRecordDTO;

public interface ErrorRecordService {
    ErrorRecordDTO saveErrorRecord(ErrorRecordDTO dto);
}
