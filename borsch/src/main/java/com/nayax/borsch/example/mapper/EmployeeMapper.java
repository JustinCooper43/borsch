package com.nayax.borsch.example.mapper;

import com.nayax.borsch.example.models.dto.EmployeeAddDto;
import com.nayax.borsch.example.models.dto.EmployeeResponseDto;
import com.nayax.borsch.example.models.entity.Employee;

public interface EmployeeMapper {

    EmployeeResponseDto toDto(Employee entity);
    Employee toAddEntity(EmployeeAddDto dto);
}
