package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.user.request.ReqUserAddDto;
import com.nayax.borsch.model.dto.user.request.ReqUserUpdateDto;
import com.nayax.borsch.model.dto.user.response.RespUserDto;
import com.nayax.borsch.model.dto.user.response.nested.RoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class UserController {

//    @Autowired
//    UserService service;

    private RespUserDto getUserMock() {
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
        return user;
    }

    @PostMapping
    public ResponseEntity<ResponseDto<RespUserDto>> add(@RequestBody ReqUserAddDto dto) {
        RespUserDto user = getUserMock();
        ResponseDto<RespUserDto> responseDto = new ResponseDto<>(user);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<RespUserDto>> get(@PathVariable(value = "id") Long id) {
        RespUserDto user = getUserMock();
        ResponseDto<RespUserDto> responseDto = new ResponseDto<>(user);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<RespUserDto>> update(@PathVariable(value = "id") Long id, @RequestBody ReqUserUpdateDto dto) {
        dto.setId(id);
        RespUserDto user = getUserMock();
        ResponseDto<RespUserDto> responseDto = new ResponseDto<>(user);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<RespUserDto>> delete(@PathVariable(value = "id") Long id) {
        RespUserDto user = getUserMock();
        ResponseDto<RespUserDto> responseDto = new ResponseDto<>(user);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<RespUserDto>>> getAll() {
        RespUserDto user = getUserMock();
        ResponseDto<List<RespUserDto>> responseDto = new ResponseDto<>(List.of(user, user, user, user, user, user, user));
        return ResponseEntity.ok(responseDto);
    }
}
