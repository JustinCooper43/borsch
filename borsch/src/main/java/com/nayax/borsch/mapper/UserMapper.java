package com.nayax.borsch.mapper;

import com.nayax.borsch.model.dto.user.request.ReqUserAddDto;
import com.nayax.borsch.model.dto.user.request.ReqUserUpdateDto;
import com.nayax.borsch.model.dto.user.response.RespUserDto;
import com.nayax.borsch.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(source = "roleName", target = "role.name")
    @Mapping(source = "roleId", target = "role.id")
    //@Mapping(expression = "java(entity.getFirstName()+ \" \" +entity.getLastName())", target = "name")
    RespUserDto toDto(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "roleId", ignore = true)
    @Mapping(target = "roleName", ignore = true)
//    @Mapping(source = "name", target = "firstName", qualifiedByName = "splitFirstName")
//    @Mapping(source = "name", target = "lastName", qualifiedByName = "splitLastName")
    UserEntity toAddEntity(ReqUserAddDto dto);

    @Mapping(target = "roleName", ignore = true)
    UserEntity toUpdateEntity(ReqUserUpdateDto dto);

//    @Named("splitFirstName")
//    public static String splitFirstName(String name) {
//        String[] strings = name.split(" ");
//        return strings[0];
//    }
//
//    @Named("splitLastName")
//    public static String splitLastName(String name) {
//        String[] strings = name.split(" ");
//        return strings.length > 1 ? strings[1] : "";
//    }


}
