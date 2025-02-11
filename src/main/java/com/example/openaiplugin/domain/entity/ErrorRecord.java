package com.example.openaiplugin.domain.entity;

import com.example.openaiplugin.constants.Constants;
import com.example.openaiplugin.domain.enumeration.ResponseStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Constants.ERROR_RECORD)
public class ErrorRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String error;

    @Column(nullable = false)
    private String service;

    private String thrownMethod;

    @Enumerated(EnumType.STRING)
    private ResponseStatus responseStatus;

    private String description;
}
