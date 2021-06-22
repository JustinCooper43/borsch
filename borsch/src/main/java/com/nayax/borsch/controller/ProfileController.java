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

    public static RespUserDto getUserMock() {
        RespUserDto user = new RespUserDto();
        user.setId(14L);
        user.setFirstName("Fname");
        user.setLastName("Lname");
        user.seteMail("adress@server.com");
        RoleDto role = new RoleDto();
        role.setId(2L);
        role.setName("Cashier");
        user.setRole(role);
        user.setPhone("+380123456789");
        return user;
    }

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
        RespUserDto user = getUserMock();
        RespCashierDto payments = PaymentController.generatorPayDto().getPaymentMethod();
        RespProfileDto profile = new RespProfileDto();
        profile.setUser(user);
        profile.setPayments(payments);
        ResponseDto<RespProfileDto> responseDto = new ResponseDto<>(profile);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<RespProfileDto>> delete(@PathVariable(value = "id") Long id) {
        RespUserDto user = getUserMock();
        RespCashierDto payments = PaymentController.generatorPayDto().getPaymentMethod();
        RespProfileDto profile = new RespProfileDto();
        profile.setUser(user);
        profile.setPayments(payments);
        ResponseDto<RespProfileDto> responseDto = new ResponseDto<>(profile);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<RespProfileDto>>> getAll() {

        ResponseDto<List<RespProfileDto>> responseDto = service.getAll();

        return ResponseEntity.ok(responseDto);
    }
}
