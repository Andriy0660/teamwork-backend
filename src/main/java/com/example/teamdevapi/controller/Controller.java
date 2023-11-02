package com.example.teamdevapi.controller;

import com.example.teamdevapi.dto.request.CalculateSalaryRequest;
import com.example.teamdevapi.dto.request.RecordRequest;
import com.example.teamdevapi.dto.response.CalculateSalaryResponse;
import com.example.teamdevapi.entity.Employee;
import com.example.teamdevapi.entity.Record;
import com.example.teamdevapi.service.EmployeeService;
import com.example.teamdevapi.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("carwash/api/v1")
@RequiredArgsConstructor
public class Controller {
    private final EmployeeService employeeService;
    private final RecordService recordService;

    @PostMapping
    public ResponseEntity<Void> addRecord(@RequestBody RecordRequest request){
        Record record = Record.builder()
                .price(request.getPrice())
                .date(LocalDate.now())
                .numberOfEmployee(request.getEmpNames().size())
                .build();

        recordService.save(record);
        HashSet<Employee> employeesWhoWorked = employeeService.findAllByNameIsIn(request.getEmpNames());

        employeesWhoWorked.forEach(e -> {
            e.getRecords().add(record);
            employeeService.save(e);
        });
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<CalculateSalaryResponse> calculateSalary(@RequestBody CalculateSalaryRequest request){
        Employee employee = employeeService.findByName(request.getEmpName());

        List<Record> recordsForEmployeeInParticularDay =
            employee.getRecords().stream()
                    .filter(e -> e.getDate().equals(request.getDate()))
                    .toList();

        double salary = recordsForEmployeeInParticularDay.stream()
                .map(e -> ( e.getPrice() / 2) / e.getNumberOfEmployee())
                .mapToDouble(e -> e).sum();

        CalculateSalaryResponse response = CalculateSalaryResponse.builder()
                .salary(salary)
                .build();
        return ResponseEntity.ok(response);
    }
}