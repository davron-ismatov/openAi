package com.example.openaiplugin.service.repository;

import com.example.openaiplugin.domain.entity.ErrorRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorRecordRepository extends JpaRepository<ErrorRecord,Long> {
}
