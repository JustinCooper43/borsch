package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.user.request.ReqUserAddDto;
import com.nayax.borsch.model.dto.user.request.ReqUserUpdateDto;
import com.nayax.borsch.model.dto.user.response.RespUserDto;
import com.nayax.borsch.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test/user")
public class UserTestController {
    @Autowired
    UserService service;

    @PostMapping("/add")
    public ResponseEntity<ResponseDto<RespUserDto>> add(@RequestBody ReqUserAddDto dto) {
        ResponseDto<RespUserDto> responseDto = service.add(dto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseDto<RespUserDto>> get(@RequestParam Long id) {
        ResponseDto<RespUserDto> responseDto = service.get(id);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDto<RespUserDto>> update(@RequestBody ReqUserUpdateDto dto) {
        ResponseDto<RespUserDto> responseDto = service.update(dto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto<RespUserDto>> delete(@RequestParam Long id) {
        ResponseDto<RespUserDto> responseDto = service.delete(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<RespUserDto>>> getAll() {
        ResponseDto<List<RespUserDto>> responseDto = service.getAll();
        return ResponseEntity.ok(responseDto);
    }
}
