package com.nayax.borsch.example.service.impl;


import com.nayax.borsch.example.mapper.EmployeeMapper;
import com.nayax.borsch.example.model.Employee;
import com.nayax.borsch.example.model.EmployeeResponseDto;
import com.nayax.borsch.example.model.dto.EmployeeAddDto;
import com.nayax.borsch.example.model.response.ResponseDto;
import com.nayax.borsch.example.repository.impl.RepositoryWImpl;
import com.nayax.borsch.example.service.ServiceW;
import com.nayax.borsch.model.dto.ErrorDto;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceWImpl implements ServiceW {

    @Autowired
    RepositoryWImpl repository;


    @Override
    public ResponseDto<EmployeeResponseDto> add(EmployeeAddDto employee) {
        System.out.println("Service method add entry");
        Employee entity = repository.add(Mappers.getMapper(EmployeeMapper.class).toEntity(employee));
        EmployeeResponseDto employeeResponseDto = Mappers.getMapper(EmployeeMapper.class).toDto(entity);
        System.out.println("Service method add exit");
        return new ResponseDto<>(employeeResponseDto);
    }

    @Override
    public ResponseDto<EmployeeResponseDto> get(Long index) {

        System.out.println("Service method get entry");
        ResponseDto<EmployeeResponseDto> responseDto;
        Optional<Employee> employee = repository.get(index);
        List<ErrorDto> listError;

        if (employee.isPresent()) {
            EmployeeResponseDto employeeResponseDto = Mappers.getMapper(EmployeeMapper.class).toDto(employee.get());
            responseDto = new ResponseDto<>(employeeResponseDto);
        } else {
            ErrorDto e = new ErrorDto();
            e.setMessage("Employee this id " + index + " not found");
            listError = List.of(e);
            responseDto = new ResponseDto<>(listError);
        }

        System.out.println("Service method get exit");

        return responseDto;
    }
}
