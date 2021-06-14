package com.nayax.borsch.example.mapper;

import com.nayax.borsch.example.model.Employee;
import com.nayax.borsch.example.model.EmployeeResponseDto;
import org.mapstruct.Mapper;

@Mapper
public interface EmployeeMapper {
    EmployeeResponseDto toDto (Employee entity);
}
