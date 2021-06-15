package com.nayax.borsch.example.mapper;

import com.nayax.borsch.example.model.Employee;
import com.nayax.borsch.example.model.EmployeeResponseDto;
import com.nayax.borsch.example.model.dto.EmployeeAddDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EmployeeMapper {
    EmployeeResponseDto toDto (Employee entity);
    @Mapping(target = "id", ignore=true)
    Employee toEntity (EmployeeAddDto dto);
}
