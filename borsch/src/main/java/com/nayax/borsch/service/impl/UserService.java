package com.nayax.borsch.service.impl;


import com.nayax.borsch.mapper.UserMapper;
import com.nayax.borsch.model.dto.request.UserAddDto;
import com.nayax.borsch.model.dto.request.UserFilterDto;
import com.nayax.borsch.model.dto.request.UserUpdateDto;
import com.nayax.borsch.model.dto.response.ErrorDto;
import com.nayax.borsch.model.dto.response.ResponseDto;
import com.nayax.borsch.model.dto.response.user.UserResponseDto;
import com.nayax.borsch.model.entity.UserEntity;
import com.nayax.borsch.repository.impl.RepositoryUserImplementation;
import com.nayax.borsch.service.UserServiceInterface;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    RepositoryUserImplementation repository;


    @Override
    public ResponseDto<UserResponseDto> add(UserAddDto dto) {
        UserEntity user = repository.add(Mappers.getMapper(UserMapper.class).toAddEntity(dto));
        UserResponseDto userResponseDto = Mappers.getMapper(UserMapper.class).toDto(user);
        return new ResponseDto<>(userResponseDto);
    }

    @Override
    public ResponseDto<UserResponseDto> update(UserUpdateDto dto) {
        UserEntity user = repository.update(Mappers.getMapper(UserMapper.class).toUpdateEntity(dto));
        UserResponseDto userResponseDto = Mappers.getMapper(UserMapper.class).toDto(user);
        return new ResponseDto<>(userResponseDto);
    }

    @Override
    public ResponseDto<UserResponseDto> get(Long id) {
        Optional<UserEntity> responseEntity = repository.findById(id);
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
        Optional <UserEntity> responseEntity = repository.findById(id);
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
        List<UserEntity> users = repository.findAll();
        List<UserResponseDto> userResponseDtoList = users.stream().map(this::toDto).collect(Collectors.toList());
        return new ResponseDto<>(userResponseDtoList);
    }

    @Override
    public ResponseDto<List<UserResponseDto>> getAllByFilter(UserFilterDto filter) {
        List<UserEntity> users = repository.getAllByFilter(filter);
        List<UserResponseDto> userResponseDtoList = users.stream().map(e->Mappers.getMapper(UserMapper.class).toDto(e)).collect(Collectors.toList());

        return new ResponseDto<>(userResponseDtoList);
    }

    private UserResponseDto toDto(UserEntity e) {
        return Mappers.getMapper(UserMapper.class).toDto(e);
    }
}
