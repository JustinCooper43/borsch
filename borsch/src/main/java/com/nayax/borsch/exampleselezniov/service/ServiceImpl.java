package com.nayax.borsch.exampleselezniov.service;

import com.nayax.borsch.exampleselezniov.mapper.EmployeeMapper;
import com.nayax.borsch.exampleselezniov.model.dto.EmployeeAddDto;
import com.nayax.borsch.exampleselezniov.model.dto.EmployeeResponseDto;
import com.nayax.borsch.exampleselezniov.model.dto.ResponseDto;
import com.nayax.borsch.exampleselezniov.model.entity.Employee;
import com.nayax.borsch.exampleselezniov.repository.Repository;
import com.nayax.borsch.model.dto.response.ErrorDto;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {
    @Autowired
    private Repository repository;

    @Override
    public ResponseDto<EmployeeResponseDto> add(EmployeeAddDto dto) {
        System.out.println("####### Started service.add #######");

        System.out.println("####### Returning from service.add #######");
        return null;
    }

    @Override
    public ResponseDto<EmployeeResponseDto> get(Long id) {
        System.out.println("####### Started service.get #######");
        Optional<Employee> responseEntity = repository.get(id);
        ResponseDto<EmployeeResponseDto> response = new ResponseDto<>();
        if (responseEntity.isPresent()) {
            response.setData(Mappers.getMapper(EmployeeMapper.class).toDto(responseEntity.get()));
        } else {
            ErrorDto e = new ErrorDto();
            e.setCause("No employee found for id " + id);
            response.setErrors(List.of(e));
        }
        System.out.println("####### Returning from service.get #######");
        return response;
    }
}
