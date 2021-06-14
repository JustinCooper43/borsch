package com.nayax.borsch.exampleselezniov.service;

import com.nayax.borsch.exampleselezniov.model.dto.EmployeeAddDto;
import com.nayax.borsch.exampleselezniov.model.dto.EmployeeResponseDto;
import com.nayax.borsch.exampleselezniov.model.dto.ResponseDto;

public interface Service {
    ResponseDto<EmployeeResponseDto> add(EmployeeAddDto dto);

    ResponseDto<EmployeeResponseDto> get(Long id);
}
