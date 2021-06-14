package com.nayax.borsch.example.model;

import java.math.BigDecimal;

public class EmployeeResponseDto {

    private Long id;
    private String FName;
    private String LName;
    private BigDecimal salary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "EmployeeResponseDto{" +
                "id=" + id +
                ", FName='" + FName + '\'' +
                ", LName='" + LName + '\'' +
                ", salary=" + salary +
                '}';
    }
}
