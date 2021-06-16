package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.user.request.ReqUserAddDto;
import com.nayax.borsch.model.dto.user.response.RespLoginDto;
import com.nayax.borsch.model.dto.user.response.RespUserDto;
import com.nayax.borsch.model.dto.user.response.nested.RoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/")
public class LoginController {

    private RespLoginDto getLoginMock() {
        RespLoginDto loginDto = new RespLoginDto();
        RespUserDto user = new RespUserDto();
        user.setId(14L);
        user.setFirstName("Fname");
        user.setLastName("Lname");
        user.seteMail("adress@server.com");
        RoleDto role = new RoleDto();
        role.setId(2L);
        role.setName("Cashier");
        user.setRole(role);
        user.setPhoneNumber("+380123456789");
        loginDto.setUser(user);
        loginDto.setTime(LocalDateTime.now());
        return loginDto;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<RespLoginDto>> login(@RequestParam String email) {
        RespLoginDto user = getLoginMock();
        ResponseDto<RespLoginDto> responseDto = new ResponseDto<>(user);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/logout")
    public boolean logout() {
        return true;
    }

    @PostMapping("/sign")
    public ResponseEntity<ResponseDto<RespLoginDto>> register(@RequestBody ReqUserAddDto dto) {
        RespLoginDto user = getLoginMock();
        ResponseDto<RespLoginDto> responseDto = new ResponseDto<>(user);
        return ResponseEntity.ok(responseDto);
    }
}
