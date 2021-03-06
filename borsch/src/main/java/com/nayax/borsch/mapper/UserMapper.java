package com.nayax.borsch.mapper;

import com.nayax.borsch.model.dto.user.request.ReqUserAddDto;
import com.nayax.borsch.model.dto.user.request.ReqUserUpdateDto;
import com.nayax.borsch.model.dto.user.response.RespUserDto;
import com.nayax.borsch.model.dto.user.response.nested.RespLoginCashierDto;
import com.nayax.borsch.model.entity.user.ProfileEntity;
import com.nayax.borsch.model.entity.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(source = "roleName", target = "role.name")
    @Mapping(source = "roleId", target = "role.id")
    //@Mapping(expression = "java(entity.getFirstName()+ \" \" +entity.getLastName())", target = "name")
    RespUserDto toDto(UserEntity entity);

    @Mapping(target = "cashier", ignore = true)
    @Mapping(source = "userEntity.roleId", target = "role.id")
    @Mapping(source = "userEntity.roleName", target = "role.name")
    @Mapping(source = "userEntity.phone", target = "phone")
    @Mapping(source = "userEntity.id", target = "id")
    @Mapping(source = "userEntity.eMail", target = "eMail")
    @Mapping(source = "userEntity.firstName", target = "firstName")
    @Mapping(source = "userEntity.lastName", target = "lastName")
    RespLoginCashierDto toLoginDto(ProfileEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "roleId", ignore = true)
    @Mapping(target = "roleName", ignore = true)
    UserEntity toAddEntity(ReqUserAddDto dto);

    @Mapping(target = "roleName", ignore = true)
    @Mapping(target = "active", ignore = true)
    UserEntity toUpdateEntity(ReqUserUpdateDto dto);

    @Mapping(target = "cashier", ignore = true)
    RespLoginCashierDto userToLoginNoCashier(RespUserDto dto);
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
