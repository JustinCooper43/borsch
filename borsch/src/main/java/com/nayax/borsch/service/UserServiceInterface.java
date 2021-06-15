package com.nayax.borsch.service;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.user.request.UserAddDto;
import com.nayax.borsch.model.dto.user.request.UserFilterDto;
import com.nayax.borsch.model.dto.user.request.UserUpdateDto;
import com.nayax.borsch.model.dto.user.response.UserResponseDto;

import java.util.List;

public interface UserServiceInterface {

    ResponseDto<UserResponseDto> add(UserAddDto dto);

    ResponseDto<UserResponseDto> update(UserUpdateDto dto);

    ResponseDto<UserResponseDto> get(Long id);

    ResponseDto<UserResponseDto> delete(Long id);

    ResponseDto<List<UserResponseDto>> getAll();

    ResponseDto<List<UserResponseDto>> getAllByFilter(UserFilterDto filter);


}
