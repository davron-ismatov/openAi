package com.example.openaiplugin.service.mapper;

import com.example.openaiplugin.domain.entity.ErrorRecord;
import com.example.openaiplugin.service.dto.ErrorRecordDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ErrorRecordMapper {
    @Mapping(target = "error", source = "error")
    ErrorRecord toEntity(ErrorRecordDTO dto);

    @Mapping(target = "id", source = "id")
    ErrorRecordDTO toDto(ErrorRecord entity);
}
