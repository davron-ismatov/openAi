package com.example.openaiplugin.service.dto;

import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
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
