package com.nayax.borsch.service.impl;


import com.nayax.borsch.exceptions.NotUpdateException;
import com.nayax.borsch.mapper.UserMapper;
import com.nayax.borsch.model.dto.ErrorDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.user.request.ReqUserAddDto;
import com.nayax.borsch.model.dto.user.request.ReqUserFilterDto;
import com.nayax.borsch.model.dto.user.request.ReqUserUpdateDto;
import com.nayax.borsch.model.dto.user.response.RespUserDto;
import com.nayax.borsch.model.entity.user.UserEntity;
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
    public ResponseDto<RespUserDto> add(ReqUserAddDto dto) {
        UserEntity user = repository.add(Mappers.getMapper(UserMapper.class).toAddEntity(dto));
        RespUserDto respUserDto = Mappers.getMapper(UserMapper.class).toDto(user);
        return new ResponseDto<>(respUserDto);
    }

    @Override
    public ResponseDto<RespUserDto> update(ReqUserUpdateDto dto) {
        UserEntity user = null;

            user = repository.update(Mappers.getMapper(UserMapper.class).toUpdateEntity(dto));

        RespUserDto respUserDto = Mappers.getMapper(UserMapper.class).toDto(user);
        return new ResponseDto<>(respUserDto);
    }

    @Override
    public ResponseDto<RespUserDto> get(Long id) {
        Optional<UserEntity> responseEntity = repository.findById(id);
        ResponseDto<RespUserDto> response = new ResponseDto<>();
        if (responseEntity.isPresent()) {
            response.setData(Mappers.getMapper(UserMapper.class).toDto(responseEntity.get()));
        } else {
            ErrorDto e = new ErrorDto();
            e.setMessage("User is not found by id " + id);
            response.setErrors(List.of(e));
        }
        return response;
    }

    @Override
    public ResponseDto<RespUserDto> delete(Long id) {
        Optional<UserEntity> responseEntity = repository.findById(id);
        ResponseDto<RespUserDto> response = new ResponseDto<>();
        if (responseEntity.isPresent() && repository.delete(id)) {
            response.setData(Mappers.getMapper(UserMapper.class).toDto(responseEntity.get()));
        } else {
            ErrorDto e = new ErrorDto();
            e.setMessage("User is not delete because this id not found in database: Id : " + id);
            response.setErrors(List.of(e));
        }
        return response;
    }

    @Override
    public ResponseDto<List<RespUserDto>> getAll() {
        List<UserEntity> users = repository.findAll();
        List<RespUserDto> respUserDtoList = users.stream().map(this::toDto).collect(Collectors.toList());
        return new ResponseDto<>(respUserDtoList);
    }

    @Override
    public ResponseDto<List<RespUserDto>> getAllByFilter(ReqUserFilterDto filter) {
        List<UserEntity> users = repository.getAllByFilter(filter);
        List<RespUserDto> respUserDtoList = users.stream().map(e -> Mappers.getMapper(UserMapper.class).toDto(e)).collect(Collectors.toList());

        return new ResponseDto<>(respUserDtoList);
    }

    private RespUserDto toDto(UserEntity e) {
        return Mappers.getMapper(UserMapper.class).toDto(e);
    }
}
