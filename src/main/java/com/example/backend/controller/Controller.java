package com.example.backend.controller;

import com.example.backend.dto.request.RecordRequest;
import com.example.backend.dto.response.AllEmpsResponse;
import com.example.backend.dto.response.CalculateSalaryResponse;
import com.example.backend.entity.Employee;
import com.example.backend.entity.Record;
import com.example.backend.service.EmployeeService;
import com.example.backend.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
    public ResponseEntity<Void> addRecord(@RequestBody RecordRequest request) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\admin-kv\\IdeaProjects\\teamwork\\backend\\src\\main\\resources\\static\\records.txt",true));
        writer.write(request.getEmpNames() + " - " + request.getPrice() + " - "+ LocalDate.now());
        writer.close();
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

    @GetMapping("/salary")
    public ResponseEntity<CalculateSalaryResponse> calculateSalary(
            @RequestParam(name = "empName") String empName,
            @RequestParam(name = "date") LocalDate date){
        Employee employee = employeeService.findByName(empName);

        List<Record> recordsForEmployeeInParticularDay =
            employee.getRecords().stream()
                    .filter(e -> e.getDate().equals(date))
                    .toList();

        double salary = recordsForEmployeeInParticularDay.stream()
                .map(e -> ( e.getPrice() / 2) / e.getNumberOfEmployee())
                .mapToDouble(e -> e).sum();

        CalculateSalaryResponse response = CalculateSalaryResponse.builder()
                .salary(salary)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<AllEmpsResponse> getNamesOfAllEmps(){
        AllEmpsResponse response = AllEmpsResponse.builder()
                .namesOfAllEmps(employeeService.getNamesOfAllEmps())
                .build();
        return ResponseEntity.ok(response);
    }
}
