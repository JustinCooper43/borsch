package com.nayax.borsch.examplestreltsov.service;

import com.nayax.borsch.examplestreltsov.models.dto.EmployeeAddDto;
import com.nayax.borsch.examplestreltsov.models.dto.EmployeeResponseDto;
import com.nayax.borsch.model.dto.response.ResponseDto;

public interface ServiceInterface {

    ResponseDto<EmployeeResponseDto> add(EmployeeAddDto dto);

    ResponseDto<EmployeeResponseDto> get(Long id);
}
