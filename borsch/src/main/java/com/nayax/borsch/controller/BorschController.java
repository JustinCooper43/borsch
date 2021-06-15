package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.user.response.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BorschController {
    @GetMapping("/hello")
    public ResponseEntity<?> greetings() {
        return ResponseEntity.ok().body(new UserResponseDto());
    }

    @GetMapping("/user")
    public ResponseEntity<ResponseDto<UserResponseDto>> getUserExample() {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(1L);
        userResponseDto.seteMail("efil@gmail.com");
        userResponseDto.setName("John Dou");
        return ResponseEntity.ok().body(new ResponseDto<>(userResponseDto));
    }
}
