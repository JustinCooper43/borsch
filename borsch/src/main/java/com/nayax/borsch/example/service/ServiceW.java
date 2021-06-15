package com.nayax.borsch.example.service;

import com.nayax.borsch.example.model.dto.EmployeeAddDto;
import com.nayax.borsch.example.model.EmployeeResponseDto;
import com.nayax.borsch.example.model.response.ResponseDto;

public interface ServiceW {

  ResponseDto <EmployeeResponseDto> add(EmployeeAddDto employee);
  ResponseDto<EmployeeResponseDto> get(Long index);
}
