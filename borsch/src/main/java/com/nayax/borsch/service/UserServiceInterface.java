package com.nayax.borsch.service;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.user.request.ReqUserAddDto;
import com.nayax.borsch.model.dto.user.request.ReqUserFilterDto;
import com.nayax.borsch.model.dto.user.request.ReqUserUpdateDto;
import com.nayax.borsch.model.dto.user.response.RespUserDto;

import java.util.List;

public interface UserServiceInterface {

    ResponseDto<RespUserDto> add(ReqUserAddDto dto);

    ResponseDto<RespUserDto> update(ReqUserUpdateDto dto);

    ResponseDto<RespUserDto> get(Long id);

    ResponseDto<RespUserDto> delete(Long id);

    ResponseDto<List<RespUserDto>> getAll();

    ResponseDto<List<RespUserDto>> getAllByFilter(ReqUserFilterDto filter);


}
