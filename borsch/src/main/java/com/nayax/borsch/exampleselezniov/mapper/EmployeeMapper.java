package com.nayax.borsch.exampleselezniov.mapper;

import com.nayax.borsch.exampleselezniov.model.dto.EmployeeAddDto;
import com.nayax.borsch.exampleselezniov.model.dto.EmployeeResponseDto;
import com.nayax.borsch.exampleselezniov.model.entity.Employee;
import org.mapstruct.Mapper;

@Mapper
public interface EmployeeMapper {
    EmployeeResponseDto toDto(Employee entity);
    Employee toAddEntity(EmployeeAddDto dto);
}
