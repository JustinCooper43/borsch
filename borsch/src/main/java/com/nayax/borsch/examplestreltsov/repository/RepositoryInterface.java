package com.nayax.borsch.examplestreltsov.repository;

import com.nayax.borsch.examplestreltsov.models.entity.Employee;

import java.util.Optional;

public interface RepositoryInterface {

    Employee add(Employee entity);

    Optional<Employee> get(Long id);
}
