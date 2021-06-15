package com.nayax.borsch.service.impl;


import com.nayax.borsch.exampleselezniov.mapper.EmployeeMapper;
import com.nayax.borsch.exampleselezniov.model.dto.EmployeeResponseDto;
import com.nayax.borsch.exampleselezniov.model.entity.Employee;
import com.nayax.borsch.model.dto.request.UserAddDto;
import com.nayax.borsch.model.dto.response.ErrorDto;
import com.nayax.borsch.model.dto.response.ResponseDto;
import com.nayax.borsch.model.dto.response.user.UserResponseDto;
import com.nayax.borsch.service.GenericService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements GenericService<UserAddDto, UserUpdateDto, UserFilterDto, UserResponseDto> {

    @Autowired
    RepositoryUserImplementation repository;


    @Override
    public ResponseDto<UserResponseDto> add(UserAddDto dto) {
        User user = repository.add(Mappers.getMapper(UserMapper.class).toAddEntity(dto));
        UserResponseDto userResponseDto = Mappers.getMapper(UserMapper.class).toDto(user);
        return new ResponseDto<>(userResponseDto);
    }

    @Override
    public ResponseDto<UserResponseDto> update(UserUpdateDto dto) {
        User user = repository.update(Mappers.getMapper(UserMapper.class).toUpdateEntity(dto));
        UserResponseDto userResponseDto = Mappers.getMapper(UserMapper.class).toDto(user);
        return new ResponseDto<>(userResponseDto);
    }

    @Override
    public ResponseDto<UserResponseDto> get(Long id) {
        Optional<User> responseEntity = repository.findBy(id);
        ResponseDto<UserResponseDto> response = new ResponseDto<>();
        if (responseEntity.isPresent()) {
            response.setData(Mappers.getMapper(UserMapper.class).toDto(responseEntity.get()));
        } else {
            ErrorDto e = new ErrorDto();
            e.setCause("User is not found by id " + id);
            response.setErrors(List.of(e));
        }
        return response;
    }

    @Override
    public ResponseDto<UserResponseDto> delete(Long id) {
        Optional <User> responseEntity = repository.findBy(id);
        ResponseDto<UserResponseDto> response = new ResponseDto<>();
        if (responseEntity.isPresent() &&  repository.delete(id)) {
            response.setData(Mappers.getMapper(UserMapper.class).toDto(responseEntity.get()));
        } else {
            ErrorDto e = new ErrorDto();
            e.setCause("User is not delete because this id not found in database: Id : " + id);
            response.setErrors(List.of(e));
        }
        return  response;
    }

    @Override
    public ResponseDto<List<UserResponseDto>> getAll() {
        List<User> users = repository.findAll();
        List<UserResponseDto> userResponseDtoList = users.stream().map(UserMapper::toDto).collect(Collectors.toList());
        return new ResponseDto<>(userResponseDtoList);
    }

    @Override
    public ResponseDto<List<UserResponseDto>> getAllByFilter(UserFilterDto filter) {
        List<User> users = repository.findByFilter(filter);
        List<UserResponseDto> userResponseDtoList = users.stream().map(UserMapper::toDto).collect(Collectors.toList());

        return new ResponseDto<>(userResponseDtoList);
    }
}
