package com.example.teamdevapi.service;

import com.example.teamdevapi.entity.Record;
import com.example.teamdevapi.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository repository;
    public void save(Record record) { repository.save(record); }
}
