package com.nayax.borsch.examplestreltsov.mapper;

import com.nayax.borsch.examplestreltsov.models.dto.EmployeeAddDto;
import com.nayax.borsch.examplestreltsov.models.dto.EmployeeResponseDto;
import com.nayax.borsch.examplestreltsov.models.entity.Employee;

public interface EmployeeMapper {

    EmployeeResponseDto toDto(Employee entity);
    Employee toAddEntity(EmployeeAddDto dto);
}
