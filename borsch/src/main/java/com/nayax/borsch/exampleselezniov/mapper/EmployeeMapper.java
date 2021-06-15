package com.nayax.borsch.exampleselezniov.mapper;

import com.nayax.borsch.exampleselezniov.model.dto.EmployeeAddDto;
import com.nayax.borsch.exampleselezniov.model.dto.EmployeeResponseDto;
import com.nayax.borsch.exampleselezniov.model.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EmployeeMapper {
    EmployeeResponseDto toDto(Employee entity);
    @Mapping(target = "id", ignore=true)
    Employee toAddEntity(EmployeeAddDto dto);
}
