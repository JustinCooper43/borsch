package com.nayax.borsch.mapper;

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
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProfileMapper {

    default RespProfileDto toDto(ProfileEntity entity) {
        RespUserDto userDto = (Mappers.getMapper(UserMapper.class).toDto(entity.getUserEntity()));
        RespCashierDto cashierDto = Mappers.getMapper(CashierMapper.class).toDto(entity.getCashierEntity());
        RespProfileDto respUserDto = new RespProfileDto();
        respUserDto.setUser(userDto);
        respUserDto.setPayments(cashierDto);
        return respUserDto;
    }

    default ProfileEntity toAddEntity(ReqProfileAddDto dto) {
        UserEntity userEntity = (Mappers.getMapper(UserMapper.class).toAddEntity(dto.getUser()));
        CashierEntity cashierEntity = Mappers.getMapper(CashierMapper.class).toAddEntity(dto.getPayments());
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setUserEntity(userEntity);
        profileEntity.setCashierEntity(cashierEntity);
        return profileEntity;
    }

    default ProfileEntity toUpEntity(ReqProfileUpDto dto) {
        UserEntity userEntity = (Mappers.getMapper(UserMapper.class).toUpdateEntity(dto.getUser()));
        CashierEntity cashierEntity = Mappers.getMapper(CashierMapper.class).toUpEntity(dto.getPayments());
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setUserEntity(userEntity);
        profileEntity.setCashierEntity(cashierEntity);
        return profileEntity;
    }

}
