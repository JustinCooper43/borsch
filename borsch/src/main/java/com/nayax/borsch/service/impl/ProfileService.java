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
import com.nayax.borsch.repository.impl.RepositoryOrderSummaryImpl;
import com.nayax.borsch.validation.enums.ValidationAction;
import com.nayax.borsch.validation.testvalid.config.ConfigRepo;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    @Autowired
    ProfileRepositoryImplementation repo;

    public ResponseDto<RespProfileDto> add(ReqProfileAddDto dto) {
        ProfileEntity entity = ProfileMapper.toAddEntity(dto);
        entity.getUserEntity().setActive("Y");
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
        Long currentCashierId = null;
        Long currentUserId = null;
        ProfileEntity cashier = null;
        ProfileEntity user = null;
        RespLoginCashierDto dto = new RespLoginCashierDto();
        ResponseDto<RespLoginCashierDto> response = new ResponseDto<>();
        List<ErrorDto> listErrors = new ArrayList<>();
        if (repo.getCurrentCashierUserIdByEmail(email).isPresent()) {
            currentCashierId = repo.getCurrentCashierUserIdByEmail(email).get();
        } else {
            ErrorDto e = new ErrorDto();
            e.setMessage("Cashier Id by email %s not found " + email);
            listErrors.add(e);
        }
        if (repo.getCurrentUserIdByEmail(email).isPresent()) {
            currentUserId = repo.getCurrentUserIdByEmail(email).get();
        } else {
            ErrorDto e = new ErrorDto();
            e.setMessage("User Id by email %s not found " + email);
            listErrors.add(e);
        }
        if (repo.findById(currentCashierId).isPresent()) {
            cashier = repo.findById(currentCashierId).get();
        } else {
            ErrorDto e = new ErrorDto();
            e.setMessage("Cashier by id %s not found " + currentCashierId);
            listErrors.add(e);
        }
        if (repo.findById(currentUserId).isPresent()) {
            user = repo.findById(currentUserId).get();

        } else {
            ErrorDto e = new ErrorDto();
            e.setMessage("User by id %s not found " + currentUserId);
            listErrors.add(e);
        }
        if (cashier != null && user != null) {
            if (Objects.equals(cashier.getUserEntity().getId(), user.getUserEntity().getId())) {
                dto.setCashier(true);
                dto.setId(cashier.getUserEntity().getId());
                dto.setPhone(cashier.getUserEntity().getPhone());
                dto.setFirstName(cashier.getUserEntity().getFirstName());
                dto.setLastName(cashier.getUserEntity().getLastName());
                dto.seteMail(cashier.getUserEntity().geteMail());
                RoleDto roleDto = new RoleDto();
                roleDto.setId(cashier.getUserEntity().getRoleId());
                roleDto.setName(cashier.getUserEntity().getRoleName());
                dto.setRole(roleDto);
                response.setData(dto);
            } else {
                dto.setCashier(false);
                dto.setId(user.getUserEntity().getId());
                dto.setPhone(user.getUserEntity().getPhone());
                dto.setFirstName(user.getUserEntity().getFirstName());
                dto.setLastName(user.getUserEntity().getLastName());
                dto.seteMail(user.getUserEntity().geteMail());
                RoleDto roleDto = new RoleDto();
                roleDto.setId(user.getUserEntity().getRoleId());
                roleDto.setName(user.getUserEntity().getRoleName());
                dto.setRole(roleDto);
                response.setData(dto);
            }
        } else if (cashier == null) {
            dto.setCashier(false);
            if (user != null) {
                dto.setId(user.getUserEntity().getId());
                dto.setPhone(user.getUserEntity().getPhone());
                dto.setFirstName(user.getUserEntity().getFirstName());
                dto.setLastName(user.getUserEntity().getLastName());
                dto.seteMail(user.getUserEntity().geteMail());
                RoleDto roleDto = new RoleDto();
                roleDto.setId(user.getUserEntity().getRoleId());
                roleDto.setName(user.getUserEntity().getRoleName());
                dto.setRole(roleDto);
                response.setData(dto);
            }
        }
        response.setErrors(listErrors);
        return response;
    }

    public ResponseDto<RespLoginCashierDto> registration(ReqUserAddDto dto) {
        RespLoginCashierDto resp = new RespLoginCashierDto();
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setCashierEntity(null);
        UserEntity userEntity = Mappers.getMapper(UserMapper.class).toAddEntity(dto);
        userEntity.setActive("Y");
        profileEntity.setUserEntity(userEntity);
        RespProfileDto respProfileDto = ProfileMapper.toDto(repo.add(profileEntity));
        resp.setCashier(false);
        resp.seteMail(respProfileDto.getUser().geteMail());
        resp.setRole(respProfileDto.getUser().getRole());
        resp.setFirstName(respProfileDto.getUser().getFirstName());
        resp.setLastName(respProfileDto.getUser().getLastName());
        resp.setId(respProfileDto.getUser().getId());
        resp.setPhone(respProfileDto.getUser().getPhone());
        return new ResponseDto<>(resp);
    }


    public ResponseDto<RespProfileDto> updateCurrentCashierInSumOrd(Long id) {

        RespProfileDto respProfileDto = ProfileMapper.toDto(repo.updateCurrentCashierInSumOrd(id));

        return new ResponseDto<>(respProfileDto);
    }


    public ResponseDto<RespProfileDto> getCurrentCashier() {

        RespProfileDto respProfileDto = ProfileMapper.toDto(repo.findById(repo.latestOrderSummaryCashier()).get());

        return new ResponseDto<>(respProfileDto);
    }


}
