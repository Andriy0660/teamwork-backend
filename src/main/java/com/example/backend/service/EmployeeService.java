package com.example.backend.service;

import com.example.backend.entity.Employee;
import com.example.backend.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repository;
    public void save(Employee employee) { repository.save(employee); }
    public List<Employee> findAll(){ return repository.findAll(); }
    public List<String> getNamesOfAllEmps(){
        return findAll().stream().map(Employee::getName).toList();
    }
    public HashSet<Employee> findAllByNameIsIn(HashSet<String> names){
        return repository.findAllByNameIsIn(names);
    }
    public Employee findByName(String name){
        return repository.findByName(name);
    }

}
