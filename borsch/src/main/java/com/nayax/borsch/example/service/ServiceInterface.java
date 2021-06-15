package com.nayax.borsch.example.service;

import com.nayax.borsch.example.models.dto.EmployeeAddDto;
import com.nayax.borsch.example.models.dto.EmployeeResponseDto;
import com.nayax.borsch.model.dto.response.ResponseDto;

public interface ServiceInterface {

    ResponseDto<EmployeeResponseDto> add(EmployeeAddDto dto);

    ResponseDto<EmployeeResponseDto> get(Long id);
}
