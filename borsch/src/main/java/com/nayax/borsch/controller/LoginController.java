package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.user.request.ReqUserAddDto;
import com.nayax.borsch.model.dto.user.response.RespLoginDto;
import com.nayax.borsch.model.dto.user.response.nested.RespLoginCashierDto;
import com.nayax.borsch.model.dto.user.response.nested.RoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class LoginController {

    private RespLoginDto getLoginMock() {
        RespLoginDto loginDto = new RespLoginDto();
        RespLoginCashierDto user = new RespLoginCashierDto();
        user.setId(14L);
        user.setFirstName("Fname");
        user.setLastName("Lname");
        user.seteMail("adress@server.com");
        user.setCashier(false);
        RoleDto role = new RoleDto();
        role.setId(2L);
        role.setName("Cashier");
        user.setRole(role);
        user.setPhone("+380123456789");
        loginDto.setUser(user);
        loginDto.setTime(LocalDateTime.now());
        return loginDto;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<RespLoginDto>> login(@RequestBody String email) {
        RespLoginDto user = getLoginMock();
        ResponseDto<RespLoginDto> responseDto = new ResponseDto<>(user);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<Boolean>> logout() {
        ResponseDto<Boolean> result = new ResponseDto<>(Boolean.TRUE);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/sign")
    public ResponseEntity<ResponseDto<RespLoginDto>> register(@RequestBody ReqUserAddDto dto) {
        RespLoginDto user = getLoginMock();
        ResponseDto<RespLoginDto> responseDto = new ResponseDto<>(user);
        return ResponseEntity.ok(responseDto);
    }
}
