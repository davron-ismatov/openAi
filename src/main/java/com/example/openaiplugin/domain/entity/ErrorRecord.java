package com.example.openaiplugin.domain.entity;

import com.example.openaiplugin.constants.TableNameConstants;
import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableNameConstants.ERROR_RECORD)
public class ErrorRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String error;
    private String service;
    private String thrownMethod;
    @Enumerated(EnumType.STRING)
    private ResponseStatus responseStatus;
}
