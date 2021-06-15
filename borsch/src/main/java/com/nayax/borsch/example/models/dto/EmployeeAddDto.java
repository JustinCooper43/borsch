package com.nayax.borsch.example.models.dto;

import java.math.BigDecimal;

public class EmployeeAddDto {

    private String FirstName;
    private String LastName;
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

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
