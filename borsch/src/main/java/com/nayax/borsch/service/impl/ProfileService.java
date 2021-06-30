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
import com.nayax.borsch.model.entity.user.CashierEntity;
import com.nayax.borsch.model.entity.user.ProfileEntity;
import com.nayax.borsch.model.entity.user.UserEntity;
import com.nayax.borsch.repository.impl.ProfileRepositoryImplementation;
import com.nayax.borsch.repository.impl.RepositoryCashierImplementation;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    @Autowired
    ProfileRepositoryImplementation profileRepository;
    @Autowired
    RepositoryCashierImplementation cashierRepository;

    public ResponseDto<RespProfileDto> add(ReqProfileAddDto dto) {
        ProfileEntity entity = ProfileMapper.toAddEntity(dto);
        entity.getUserEntity().setActive("Y");
        RespProfileDto respProfileDto = ProfileMapper.toDto(profileRepository.add(entity));
        return new ResponseDto<>(respProfileDto);
    }

    public ResponseDto<RespProfileDto> update(ReqProfileUpDto dto) {
        ProfileEntity entity = ProfileMapper.toUpEntity(dto);
        Optional<CashierEntity> cashier = cashierRepository.findById(entity.getUserEntity().getId());
        entity.getCashierEntity().setUserId(entity.getUserEntity().getId());
        if (cashier.isPresent()) {
            entity.getCashierEntity().setId(cashier.get().getId());
            cashierRepository.update(entity.getCashierEntity());
        } else if (entity.getCashierEntity().isCashPaymentAllowed() || entity.getCashierEntity().getCardNumber() != null) {
            cashierRepository.add(entity.getCashierEntity());
        }
        RespProfileDto respProfileDto = ProfileMapper.toDto(profileRepository.update(entity));
        return new ResponseDto<>(respProfileDto);
    }

    public ResponseDto<RespProfileDto> delete(Long id) {
        RespProfileDto respProfileDto = ProfileMapper.toDto(profileRepository.delete(id));
        return new ResponseDto<>(respProfileDto);
    }

    public ResponseDto<RespProfileDto> getById(Long id) {
        Optional<ProfileEntity> entity = profileRepository.findById(id);
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
        List<ProfileEntity> entityList = profileRepository.findAll();
        List<RespProfileDto> respProfileDtos = entityList.stream().map(ProfileMapper::toDto).collect(Collectors.toList());
        return new ResponseDto<>(respProfileDtos);
    }

    public ResponseDto<RespLoginCashierDto> checkCashierLogining(String email) {
        Long currentCashierId;
        ProfileEntity user = null;
        List<ErrorDto> listErrors = new ArrayList<>();
        Optional<Long> currentCashier = profileRepository.getCurrentCashierUserIdByEmail(email);
        if (currentCashier.isPresent()) {
            currentCashierId = currentCashier.get();
        } else {
            currentCashierId = -1L;
            ErrorDto e = new ErrorDto();
            e.setMessage("Cashier Id by email %s not found " + email);
            listErrors.add(e);
        }
        Optional<ProfileEntity> currentUser = profileRepository.findByEmail(email);
        if (currentUser.isPresent()) {
            user = currentUser.get();
        } else {
            ErrorDto e = new ErrorDto();
            e.setMessage("User by email %s not found " + email);
            listErrors.add(e);
        }
        ResponseDto<RespLoginCashierDto> response = new ResponseDto<>();
        if (user != null) {
            RespLoginCashierDto dto = Mappers.getMapper(UserMapper.class).toLoginDto(user);
            if (user.getCashierEntity().getUserId() != null
                    && user.getCashierEntity().getUserId().equals(currentCashierId)) {
                dto.setCashier(true);
            }
            response.setData(dto);
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
        RespProfileDto respProfileDto = ProfileMapper.toDto(profileRepository.add(profileEntity));
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
        RespProfileDto respProfileDto = ProfileMapper.toDto(profileRepository.updateCurrentCashierInSumOrd(id));
        return new ResponseDto<>(respProfileDto);
    }

    public ResponseDto<RespProfileDto> getCurrentCashier() {
        RespProfileDto respProfileDto = ProfileMapper.toDto(profileRepository.findById(profileRepository.latestOrderSummaryCashier()).get());
        return new ResponseDto<>(respProfileDto);
    }
}
