package com.nayax.borsch.exampleselezniov.model.entity;

import java.math.BigDecimal;

public class Employee {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;
    private String firstName;
    private String lastName;
    private BigDecimal Salary;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return Salary;
    }

    public void setSalary(BigDecimal salary) {
        Salary = salary;
    }
}
