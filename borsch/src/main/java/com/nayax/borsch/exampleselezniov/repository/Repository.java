package com.nayax.borsch.exampleselezniov.repository;

import com.nayax.borsch.exampleselezniov.model.entity.Employee;

import java.util.Optional;

public interface Repository {
    Employee add(Employee entity);

    Optional<Employee> get(Long id);
}
