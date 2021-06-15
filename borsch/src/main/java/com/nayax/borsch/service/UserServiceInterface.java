package com.nayax.borsch.service;

import com.nayax.borsch.model.dto.request.UserAddDto;
import com.nayax.borsch.model.dto.request.UserFilterDto;
import com.nayax.borsch.model.dto.request.UserUpdateDto;
import com.nayax.borsch.model.dto.response.ResponseDto;
import com.nayax.borsch.model.dto.response.user.UserResponseDto;

import java.util.List;

public interface UserServiceInterface {

    ResponseDto<UserResponseDto> add(UserAddDto dto);
    ResponseDto<UserResponseDto> update(UserUpdateDto dto);
    ResponseDto<UserResponseDto> get(Long id);
    ResponseDto<UserResponseDto> delete(Long id);
    ResponseDto<List<UserResponseDto>> getAll();
    ResponseDto<List<UserResponseDto>> getAllByFilter(UserFilterDto filter);


}
