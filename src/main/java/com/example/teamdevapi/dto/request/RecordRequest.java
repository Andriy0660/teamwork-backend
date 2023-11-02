package com.example.teamdevapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordRequest {
    Double price;
    HashSet<String> empNames;
}
