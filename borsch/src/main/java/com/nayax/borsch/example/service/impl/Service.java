package com.nayax.borsch.example.service.impl;

import com.nayax.borsch.example.mapper.EmployeeMapper;
import com.nayax.borsch.example.models.dto.EmployeeAddDto;
import com.nayax.borsch.example.models.dto.EmployeeResponseDto;
import com.nayax.borsch.example.models.entity.Employee;
import com.nayax.borsch.example.repository.impl.Repository;
import com.nayax.borsch.example.service.ServiceInterface;
import com.nayax.borsch.model.dto.response.ErrorDto;
import com.nayax.borsch.model.dto.response.ResponseDto;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Service
public class Service implements ServiceInterface {

    @Autowired
    Repository repository;

    @Override
    public ResponseDto<EmployeeResponseDto> add(EmployeeAddDto dto) {

        Employee requestEntity = Mappers.getMapper(EmployeeMapper.class).toAddEntity(dto);
        Employee responseEntity = repository.add(requestEntity);
        EmployeeResponseDto employeeResponseDto = Mappers.getMapper(EmployeeMapper.class).toDto(responseEntity);
        ResponseDto<EmployeeResponseDto> response = new ResponseDto<>();
        response.setData(employeeResponseDto);
        return response;
    }

    @Override
    public ResponseDto<EmployeeResponseDto> get(Long id) {

        Optional<Employee> responseEntity = repository.get(id);
        ResponseDto<EmployeeResponseDto> responseDto = new ResponseDto<>();
        if(responseEntity.isPresent()){
            responseDto.setData(Mappers.getMapper(EmployeeMapper.class).toDto(responseEntity.get()));
        }else{
            ErrorDto errorDto = new ErrorDto();
            errorDto.setCause("No employee found for id " + id);
            responseDto.setErrors(List.of(errorDto));

        }
        return responseDto;
    }
}
