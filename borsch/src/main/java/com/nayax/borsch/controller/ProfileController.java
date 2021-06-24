package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.user.request.ReqProfileAddDto;
import com.nayax.borsch.model.dto.user.request.ReqProfileUpDto;
import com.nayax.borsch.model.dto.user.response.RespCashierDto;
import com.nayax.borsch.model.dto.user.response.RespProfileDto;
import com.nayax.borsch.model.dto.user.response.RespUserDto;
import com.nayax.borsch.model.dto.user.response.nested.RoleDto;
import com.nayax.borsch.service.impl.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    ProfileService service;


    @PostMapping
    public ResponseEntity<ResponseDto<RespProfileDto>> add(@RequestBody ReqProfileAddDto dto) {
        ResponseDto<RespProfileDto> responseDto = service.add(dto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<RespProfileDto>> get(@PathVariable(value = "id") Long id) {
        ResponseDto<RespProfileDto> responseDto = service.getById(id);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<RespProfileDto>> update(@PathVariable(value = "id") Long id, @RequestBody ReqProfileUpDto dto) {

        dto.getUser().setId(id);
        dto.getPayments().setCashierId(id);

        ResponseDto<RespProfileDto> responseDto = service.update(dto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<RespProfileDto>> delete(@PathVariable(value = "id") Long id) {


        ResponseDto<RespProfileDto> responseDto = service.delete(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<RespProfileDto>>> getAll() {

        ResponseDto<List<RespProfileDto>> responseDto = service.getAll();

        return ResponseEntity.ok(responseDto);
    }
}
