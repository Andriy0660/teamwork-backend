package com.example.teamdevapi.repository;

import com.example.teamdevapi.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    HashSet<Employee> findAllByNameIsIn(HashSet<String> names);
    Employee findByName(String name);
}
