package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.request.UserAddDto;
import com.nayax.borsch.model.dto.request.UserUpdateDto;
import com.nayax.borsch.model.dto.response.ResponseDto;
import com.nayax.borsch.model.dto.response.user.UserResponseDto;
import com.nayax.borsch.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("/add")
    public ResponseEntity<ResponseDto<UserResponseDto>> add(@RequestBody UserAddDto dto) {
        ResponseDto<UserResponseDto> responseDto = service.add(dto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseDto<UserResponseDto>> get(@RequestParam Long id) {
        ResponseDto<UserResponseDto> responseDto = service.get(id);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDto<UserResponseDto>> update(@RequestBody UserUpdateDto dto){
        ResponseDto<UserResponseDto> responseDto = service.update(dto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto<UserResponseDto>> delete(@RequestParam Long id) {
        ResponseDto<UserResponseDto> responseDto = service.delete(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<UserResponseDto>>> getAll() {
        ResponseDto<List<UserResponseDto>> responseDto = service.getAll();
        return ResponseEntity.ok(responseDto);
    }
}
