package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.user.response.RespUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BorschController {
    @GetMapping("/hello")
    public ResponseEntity<?> greetings() {
        return ResponseEntity.ok().body(new RespUserDto());
    }

    @GetMapping("/user")
    public ResponseEntity<ResponseDto<RespUserDto>> getUserExample() {
        RespUserDto respUserDto = new RespUserDto();
        respUserDto.setId(1L);
        respUserDto.seteMail("efil@gmail.com");
        respUserDto.setLastName("John Dou");
        return ResponseEntity.ok().body(new ResponseDto<>(respUserDto));
    }
}
