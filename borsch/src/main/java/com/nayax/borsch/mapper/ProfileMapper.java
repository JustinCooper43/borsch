package com.nayax.borsch.mapper;

import com.nayax.borsch.model.dto.user.request.ReqProfileAddDto;
import com.nayax.borsch.model.dto.user.request.ReqProfileUpDto;
import com.nayax.borsch.model.dto.user.response.RespProfileDto;
import com.nayax.borsch.model.entity.user.ProfileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProfileMapper {


//    @Mapping(source = "cashierEntity.cashierId", target = "user")
    @Mapping(source = "cashierEntity.cashPaymentAllowed", target = "payments")
    RespProfileDto toDto(ProfileEntity entity);

    ProfileEntity toAddEntity(ReqProfileAddDto addDto);

    ProfileEntity toUpEntity(ReqProfileUpDto upDto);
}
