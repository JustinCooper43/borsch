package com.nayax.borsch.example.repository;

import com.nayax.borsch.example.models.entity.Employee;

import java.util.Optional;

public interface RepositoryInterface {

    Employee add(Employee entity);

    Optional<Employee> get(Long id);
}
