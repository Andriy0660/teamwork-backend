package com.example.teamdevapi.repository;

import com.example.teamdevapi.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Integer> {
}
