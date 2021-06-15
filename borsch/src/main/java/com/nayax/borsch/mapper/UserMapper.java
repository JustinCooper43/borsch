package com.nayax.borsch.mapper;

import com.nayax.borsch.model.dto.user.request.UserAddDto;
import com.nayax.borsch.model.dto.user.request.UserUpdateDto;
import com.nayax.borsch.model.dto.user.response.UserResponseDto;
import com.nayax.borsch.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface UserMapper {

    @Mapping(source = "roleName", target = "role.name")
    @Mapping(source = "roleId", target = "role.id")
    @Mapping(expression = "java(entity.getFirstName()+ \" \" +entity.getLastName())", target = "name")
    UserResponseDto toDto(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(source = "name", target = "firstName", qualifiedByName = "splitFirstName")
    @Mapping(source = "name", target = "lastName", qualifiedByName = "splitLastName")
    UserEntity toAddEntity(UserAddDto dto);

    UserEntity toUpdateEntity(UserUpdateDto dto);

    @Named("splitFirstName")
    public static String splitFirstName(String name) {
        String[] strings = name.split(" ");
        return strings[0];
    }

    @Named("splitLastName")
    public static String splitLastName(String name) {
        String[] strings = name.split(" ");
        return strings.length > 1 ? strings[1] : "";
    }


}
