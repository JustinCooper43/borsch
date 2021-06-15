package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.request.UserAddDto;
import com.nayax.borsch.model.dto.response.ResponseDto;
import com.nayax.borsch.model.dto.response.user.UserResponseDto;
import com.nayax.borsch.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("/user/add")
    public ResponseEntity<ResponseDto<UserResponseDto>> add(@RequestBody UserAddDto dto) {
        ResponseDto<UserResponseDto> responseDto = service.add(dto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("user/get")
    public ResponseEntity<ResponseDto<UserResponseDto>> get(@RequestParam Long id) {
        ResponseDto<UserResponseDto> responseDto = service.get(id);
        return ResponseEntity.ok(responseDto);
    }
}
