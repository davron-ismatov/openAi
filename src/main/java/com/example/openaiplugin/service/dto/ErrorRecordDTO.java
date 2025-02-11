package com.example.openaiplugin.service.dto;

import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorRecordDTO {
    private Long id;
    private String error;
    private String service;
    private String thrownMethod;
    private ResponseStatus responseStatus;
    private String description;
}
