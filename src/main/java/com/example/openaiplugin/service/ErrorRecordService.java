package com.example.openaiplugin.service;

import com.example.openaiplugin.domain.enumeration.ResponseStatus;

public interface ErrorRecordService {
    void saveErrorRecord(String error, String occurredClass, String occurredMethod, ResponseStatus responseStatus);
}
