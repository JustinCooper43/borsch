package com.nayax.borsch.examplestreltsov.models.dto;

import java.math.BigDecimal;

public class EmployeeResponseDto {

    private String FirstName;
    private String LastName;
    private Long id;
    private BigDecimal salary;

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
