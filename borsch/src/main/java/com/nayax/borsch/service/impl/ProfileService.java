package com.nayax.borsch.service.impl;


import com.nayax.borsch.mapper.UserMapper;
import com.nayax.borsch.mapper.impl.ProfileMapper;
import com.nayax.borsch.model.dto.ErrorDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.user.request.ReqProfileAddDto;
import com.nayax.borsch.model.dto.user.request.ReqProfileUpDto;
import com.nayax.borsch.model.dto.user.request.ReqUserAddDto;
import com.nayax.borsch.model.dto.user.response.RespProfileDto;
import com.nayax.borsch.model.dto.user.response.nested.RespLoginCashierDto;
import com.nayax.borsch.model.dto.user.response.nested.RoleDto;
import com.nayax.borsch.model.entity.user.ProfileEntity;
import com.nayax.borsch.model.entity.user.UserEntity;
import com.nayax.borsch.repository.impl.ProfileRepositoryImplementation;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    @Autowired
    ProfileRepositoryImplementation repo;

    public ResponseDto<RespProfileDto> add(ReqProfileAddDto dto) {

        ProfileEntity entity = ProfileMapper.toAddEntity(dto);

        RespProfileDto respProfileDto = ProfileMapper.toDto(repo.add(entity));

        return new ResponseDto<>(respProfileDto);

    }

    public ResponseDto<RespProfileDto> update(ReqProfileUpDto dto) {

        ProfileEntity entity = ProfileMapper.toUpEntity(dto);

        RespProfileDto respProfileDto = ProfileMapper.toDto(repo.update(entity));

        return new ResponseDto<>(respProfileDto);

    }

    public ResponseDto<RespProfileDto> delete(Long id) {


        RespProfileDto respProfileDto = ProfileMapper.toDto(repo.delete(id));

        return new ResponseDto<>(respProfileDto);

    }

    public ResponseDto<RespProfileDto> getById(Long id) {

        Optional<ProfileEntity> entity = repo.findById(id);
        ResponseDto<RespProfileDto> response = new ResponseDto<>();
        if (entity.isPresent()) {
            response.setData(ProfileMapper.toDto(entity.get()));
        } else {
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


    public ResponseDto<RespLoginCashierDto> checkCashierLogining(String email) {
        RespLoginCashierDto respLoginCashierDto = new RespLoginCashierDto();
        Optional<ProfileEntity> entity = repo.findById(repo.getCurrentCashierUserIdByEmail(email));

        if (entity.isPresent()) {
            respLoginCashierDto.setCashier(true);
            respLoginCashierDto.seteMail(entity.get().getUserEntity().geteMail());
            respLoginCashierDto.setFirstName(entity.get().getUserEntity().getFirstName());
            respLoginCashierDto.setLastName(entity.get().getUserEntity().getLastName());
            respLoginCashierDto.setPhone(entity.get().getUserEntity().getPhone());
            respLoginCashierDto.setId(entity.get().getUserEntity().getId());

            RoleDto roleDto = new RoleDto();
            roleDto.setId(entity.get().getUserEntity().getRoleId());
            roleDto.setName(entity.get().getUserEntity().getRoleName());
            respLoginCashierDto.setRole(roleDto);

        }

        return new ResponseDto<>(respLoginCashierDto);

    }


    public ResponseDto<RespLoginCashierDto> registration(ReqUserAddDto dto) {
        RespLoginCashierDto cashierDto = new RespLoginCashierDto();
        ProfileEntity profileEntity = new ProfileEntity();
        UserEntity userEntity = Mappers.getMapper(UserMapper.class).toAddEntity(dto);
        profileEntity.setCashierEntity(null);
        profileEntity.setUserEntity(userEntity);

        RespProfileDto respProfileDto = ProfileMapper.toDto(repo.add(profileEntity));

        cashierDto.setCashier(repo.findById(repo.getCurrentCashierUserIdByEmail(dto.geteMail())).isPresent());
        cashierDto.setId(respProfileDto.getUser().getId());
        cashierDto.setRole(respProfileDto.getUser().getRole());
        cashierDto.seteMail(respProfileDto.getUser().geteMail());
        cashierDto.setFirstName(respProfileDto.getUser().getFirstName());
        cashierDto.setLastName(respProfileDto.getUser().getLastName());
        cashierDto.setPhone(respProfileDto.getUser().getPhone());

        return new ResponseDto<>(cashierDto);
    }

}
