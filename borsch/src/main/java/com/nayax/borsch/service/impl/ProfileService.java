package com.nayax.borsch.service.impl;


import com.nayax.borsch.mapper.UserMapper;
import com.nayax.borsch.mapper.impl.ProfileMapper;
import com.nayax.borsch.model.dto.ErrorDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.user.request.ReqProfileAddDto;
import com.nayax.borsch.model.dto.user.request.ReqProfileUpDto;
import com.nayax.borsch.model.dto.user.request.ReqUserAddDto;
import com.nayax.borsch.model.dto.user.response.RespLoginDto;
import com.nayax.borsch.model.dto.user.response.RespProfileDto;
import com.nayax.borsch.model.dto.user.response.nested.RespLoginCashierDto;
import com.nayax.borsch.model.entity.user.CashierEntity;
import com.nayax.borsch.model.entity.user.ProfileEntity;
import com.nayax.borsch.model.entity.user.UserEntity;
import com.nayax.borsch.repository.impl.ProfileRepositoryImplementation;
import com.nayax.borsch.repository.impl.RepositoryCashierImplementation;
import com.nayax.borsch.utility.enums.ErrorStatus;
import com.nayax.borsch.validation.config.ConfigRepo;
import com.nayax.borsch.validation.config.DrinkAdditionValidationConfig;
import com.nayax.borsch.validation.config.ProfileConfigValid;
import com.nayax.borsch.validation.enums.ValidationAction;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        List<ErrorDto> errors = ProfileConfigValid.getValidatorProfile().validate(dto, ValidationAction.PROFILE_ADD);
        if (errors.size() > 0) {
            return new ResponseDto<>(errors);
        }
        errors.addAll(ConfigRepo.getRepositoryValidator().validate(dto.getUser().geteMail(), ValidationAction.USER_ADD_EMAIL));
        if (errors.size() > 0) {
            return new ResponseDto<RespProfileDto>(errors).setStatus(ErrorStatus.UNPROCESSIBLE.statusName);
        }

        ProfileEntity entity = ProfileMapper.toAddEntity(dto);
        entity.getUserEntity().setActive("Y");
        RespProfileDto respProfileDto = ProfileMapper.toDto(profileRepository.add(entity));
        return new ResponseDto<>(respProfileDto);
    }

    public ResponseDto<RespProfileDto> update(ReqProfileUpDto dto) {
        List<ErrorDto> errors = ProfileConfigValid.getValidatorProfile().validate(dto, ValidationAction.PROFILE_UPDATE);
        if (errors.size() > 0) {
            return new ResponseDto<>(errors);
        }
        errors.addAll(ConfigRepo.getRepositoryValidator().validate(dto.getUser().getId(), ValidationAction.USER_VERIFY_ID));
        if (errors.size() > 0) {
            return new ResponseDto<RespProfileDto>(errors).setStatus(ErrorStatus.FORBIDDEN.statusName);
        }

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
        List<ErrorDto> errors = DrinkAdditionValidationConfig.getValidatorDrinkAdd().validate(id, ValidationAction.USER_VERIFY_ID);
        if (errors.size() > 0) {
            return new ResponseDto<RespProfileDto>(errors).setStatus(ErrorStatus.UNPROCESSIBLE.statusName);
        }
        errors.addAll(ConfigRepo.getRepositoryValidator().validate(id, ValidationAction.USER_VERIFY_ID));
        if (errors.size() > 0) {
            return new ResponseDto<RespProfileDto>(errors).setStatus(ErrorStatus.UNPROCESSIBLE.statusName);
        }

        RespProfileDto respProfileDto = ProfileMapper.toDto(profileRepository.deleteByUserId(id));
        return new ResponseDto<>(respProfileDto);
    }

    public ResponseDto<RespProfileDto> getById(Long id) {

        List<ErrorDto> errors = ProfileConfigValid.getValidatorProfile().validate(id, ValidationAction.PROFILE_GET);
        if (errors.size() > 0) {
            return new ResponseDto<>(errors);
        }
        errors.addAll(ConfigRepo.getRepositoryValidator().validate(id, ValidationAction.USER_VERIFY_ID));
        if (errors.size() > 0) {
            return new ResponseDto<RespProfileDto>(errors).setStatus(ErrorStatus.UNPROCESSIBLE.statusName);
        }

        Optional<ProfileEntity> entity = profileRepository.findByUserId(id);
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
            System.out.println("Cashier Id by email " + email + " not found");
//            ErrorDto e = new ErrorDto();
//            e.setMessage("Cashier Id by email %s not found " + email);
//            listErrors.add(e);
        }
        Optional<ProfileEntity> currentUser = profileRepository.findByEmail(email);
        if (currentUser.isPresent()) {
            user = currentUser.get();
        } else {
            ErrorDto e = new ErrorDto();
            e.setMessage("User by email %s not found " + email);
            listErrors.add(e);
            return new ResponseDto<RespLoginCashierDto>(listErrors).setStatus(ErrorStatus.NOT_FOUND.statusName);
        }
        ResponseDto<RespLoginCashierDto> response = new ResponseDto<>();
        RespLoginCashierDto dto = Mappers.getMapper(UserMapper.class).toLoginDto(user);
        if (user.getCashierEntity().getUserId() != null
                && user.getCashierEntity().getUserId().equals(currentCashierId)) {
            dto.setCashier(true);
        }
        response.setData(dto);
        response.setErrors(listErrors);
        return response.setStatus(ErrorStatus.OK.statusName);
    }

    public ResponseDto<RespLoginDto> registration(ReqUserAddDto dto) {
        List<ErrorDto> validationErrors = ProfileConfigValid.getValidatorProfile().validate(dto, ValidationAction.USER_ADD);
        if (validationErrors.size() > 0) {
            return new ResponseDto<RespLoginDto>(validationErrors).setStatus(ErrorStatus.UNPROCESSIBLE.statusName);
        }
        validationErrors.addAll(ConfigRepo.getRepositoryValidator().validate(dto.geteMail(), ValidationAction.USER_ADD_EMAIL));
        if (validationErrors.size() > 0) {
            return new ResponseDto<RespLoginDto>(validationErrors).setStatus(ErrorStatus.FORBIDDEN.statusName);
        }

        UserEntity userEntity = Mappers.getMapper(UserMapper.class).toAddEntity(dto);
        userEntity.setActive("Y");
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setUserEntity(userEntity);
        RespProfileDto respProfileDto = ProfileMapper.toDto(profileRepository.add(profileEntity));
        RespLoginDto response = new RespLoginDto();

        response.setUser(Mappers.getMapper(UserMapper.class).userToLoginNoCashier(respProfileDto.getUser()));
        response.setTime(LocalDateTime.now());
        return new ResponseDto<>(response).setStatus(ErrorStatus.OK.statusName);
    }

    public ResponseDto<RespProfileDto> updateCurrentCashierInSumOrd(Long cashierId) {
        RespProfileDto respProfileDto = ProfileMapper.toDto(profileRepository.updateCurrentCashierInSumOrd(cashierId));
        return new ResponseDto<>(respProfileDto);
    }

    public ResponseDto<RespProfileDto> getCurrentCashier() {
        RespProfileDto respProfileDto = ProfileMapper.toDto(profileRepository.findByUserId(profileRepository.latestOrderSummaryCashier()).get());
        return new ResponseDto<>(respProfileDto);
    }
}
