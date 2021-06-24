package com.nayax.borsch.service.impl;


import com.nayax.borsch.exceptions.NotUpdateException;
import com.nayax.borsch.mapper.CashierMapper;
import com.nayax.borsch.mapper.UserMapper;
import com.nayax.borsch.mapper.impl.ProfileMapper;
import com.nayax.borsch.model.dto.ErrorDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.user.request.ReqProfileAddDto;
import com.nayax.borsch.model.dto.user.request.ReqProfileUpDto;
import com.nayax.borsch.model.dto.user.request.ReqUserAddDto;
import com.nayax.borsch.model.dto.user.response.RespCashierDto;
import com.nayax.borsch.model.dto.user.response.RespProfileDto;
import com.nayax.borsch.model.dto.user.response.RespUserDto;
import com.nayax.borsch.model.entity.user.CashierEntity;
import com.nayax.borsch.model.entity.user.ProfileEntity;
import com.nayax.borsch.model.entity.user.UserEntity;
import com.nayax.borsch.repository.impl.ProfileRepositoryImplementation;
import com.nayax.borsch.repository.impl.RepositoryCashierImplementation;
import com.nayax.borsch.repository.impl.RepositoryUserImplementation;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    @Autowired
    ProfileRepositoryImplementation repo;

    public ResponseDto<RespProfileDto> add (ReqProfileAddDto dto) {

        ProfileEntity entity = ProfileMapper.toAddEntity(dto);

        RespProfileDto respProfileDto = ProfileMapper.toDto(repo.add(entity));

        return new ResponseDto<>(respProfileDto);

    }
    public ResponseDto<RespProfileDto> update (ReqProfileUpDto dto) {

        ProfileEntity entity = ProfileMapper.toUpEntity(dto);

        RespProfileDto respProfileDto = ProfileMapper.toDto(repo.update(entity));

        return new ResponseDto<>(respProfileDto);

    }

    public ResponseDto<RespProfileDto> delete (Long id) {


        RespProfileDto respProfileDto = ProfileMapper.toDto(repo.delete(id));

        return new ResponseDto<>(respProfileDto);

    }

  public ResponseDto<RespProfileDto> getById (Long id) {

      Optional<ProfileEntity> entity =  repo.findById(id);
      ResponseDto<RespProfileDto> response = new ResponseDto<>();
        if (entity.isPresent()){
            response.setData(ProfileMapper.toDto(entity.get()));
        }else {
            ErrorDto e = new ErrorDto();
            e.setMessage("Profile is not found by id " + id);
            response.setErrors(List.of(e));
        }
        return response;

    }


    public ResponseDto<List<RespProfileDto>> getAll() {
        List<ProfileEntity> entityList = repo.findAll();

        List<RespProfileDto> respProfileDtos = entityList.stream().map(ProfileMapper::toDto).collect(Collectors.toList());

        return new ResponseDto<>(respProfileDtos);
    }

}
