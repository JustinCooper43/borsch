package com.nayax.borsch.mapper;

import com.nayax.borsch.model.dto.user.request.UserAddDto;
import com.nayax.borsch.model.dto.user.request.UserUpdateDto;
import com.nayax.borsch.model.dto.user.response.UserResponseDto;
import com.nayax.borsch.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(source = "roleName", target = "role.name")
    @Mapping(source = "roleId", target = "role.id")
    UserResponseDto toDto(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    UserEntity toAddEntity(UserAddDto dto);

    UserEntity toUpdateEntity(UserUpdateDto dto);
}
