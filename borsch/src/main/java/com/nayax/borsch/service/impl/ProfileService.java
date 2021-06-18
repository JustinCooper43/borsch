package com.nayax.borsch.service.impl;


import com.nayax.borsch.mapper.CashierMapper;
import com.nayax.borsch.mapper.UserMapper;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.user.request.ReqUserAddDto;
import com.nayax.borsch.model.dto.user.response.RespCashierDto;
import com.nayax.borsch.model.dto.user.response.RespProfileDto;
import com.nayax.borsch.model.dto.user.response.RespUserDto;
import com.nayax.borsch.model.entity.user.ProfileEntity;
import com.nayax.borsch.model.entity.user.UserEntity;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {


    public ResponseDto<RespProfileDto> toDto(ProfileEntity entity) {
       RespUserDto user = (Mappers.getMapper(UserMapper.class).toDto(entity.getUserEntity()));
       RespCashierDto cashierDto = Mappers.getMapper(CashierMapper.class).toDto(entity.getCashierEntity());
       RespProfileDto respUserDto = new RespProfileDto();
       respUserDto.setUser(user);
       respUserDto.setPayments(cashierDto);
        return new ResponseDto<>(respUserDto);
    }



}
