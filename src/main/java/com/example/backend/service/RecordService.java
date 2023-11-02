package com.example.backend.service;

import com.example.backend.entity.Record;
import com.example.backend.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository repository;
    public void save(Record record) { repository.save(record); }
}
