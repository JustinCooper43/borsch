package com.nayax.borsch.example.repository;

import com.nayax.borsch.example.model.Employee;
import com.nayax.borsch.example.model.dto.EmployeeAddDto;

import java.util.Optional;

public interface RepositoryW {

    Employee add(EmployeeAddDto dto);
    Optional<Employee> get (Long index);
}
