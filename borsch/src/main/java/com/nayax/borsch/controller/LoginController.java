package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.user.request.ReqUserAddDto;
import com.nayax.borsch.model.dto.user.response.RespLoginDto;
import com.nayax.borsch.service.impl.ProfileService;
import com.nayax.borsch.utility.enums.ErrorStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class LoginController {

    @Autowired
    ProfileService service;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<RespLoginDto>> login(@RequestBody String email) {
        RespLoginDto respLoginDto = new RespLoginDto();
        respLoginDto.setUser(service.checkCashierLogining(email).getData());
        respLoginDto.setTime(LocalDateTime.now());
        ResponseDto<RespLoginDto> responseDto = new ResponseDto<>(respLoginDto).setStatus(ErrorStatus.OK.statusName);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<Boolean>> logout() {
        ResponseDto<Boolean> result = new ResponseDto<>(Boolean.TRUE);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/sign")
    public ResponseEntity<ResponseDto<RespLoginDto>> register(@RequestBody ReqUserAddDto dto) {
        return ResponseEntity.ok(service.registration(dto));
    }
}
