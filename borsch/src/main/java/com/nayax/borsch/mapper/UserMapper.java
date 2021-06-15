package com.nayax.borsch.mapper;

import com.nayax.borsch.model.dto.request.UserAddDto;
import com.nayax.borsch.model.dto.request.UserUpdateDto;
import com.nayax.borsch.model.dto.response.user.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(source = "entity.roleName", target = "dto.role.name")
    @Mapping(source = "entity.roleId", target = "dto.role.id")
    UserResponseDto toDto(UserEntity entity, UserResponseDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    UserEntity toAddEntity(UserAddDto dto);

    UserEntity toUpdateEntity(UserUpdateDto dto);
}
