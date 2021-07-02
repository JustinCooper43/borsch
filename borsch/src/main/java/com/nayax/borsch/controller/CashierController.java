package com.nayax.borsch.controller;


import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.user.response.RespCashierDto;
import com.nayax.borsch.model.dto.user.response.RespProfileDto;
import com.nayax.borsch.model.dto.user.response.RespUserDto;
import com.nayax.borsch.model.dto.user.response.nested.CreditCardDto;
import com.nayax.borsch.model.dto.user.response.nested.RoleDto;
import com.nayax.borsch.model.entity.user.CashierEntity;
import com.nayax.borsch.model.entity.user.ProfileEntity;
import com.nayax.borsch.model.entity.user.UserEntity;
import com.nayax.borsch.service.impl.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cashier")
public class CashierController {

    @Autowired
    ProfileService service;

    @GetMapping ("/{id}")
    public ResponseEntity<ResponseDto<RespProfileDto>> getById(@PathVariable(value = "id") Long id) {
        ResponseDto<RespProfileDto> respDto = service.getById(id);
        return ResponseEntity.ok(respDto);
    }

    @GetMapping("/dropdown")
    public ResponseEntity<ResponseDto<List<RespUserDto>>> getAllUsers() {

        ResponseDto<List<RespProfileDto>> respListDto = service.getAll();

        List<RespUserDto> respUserDtoList = new ArrayList<>();

        for(RespProfileDto respProf : respListDto.getData()){
            respUserDtoList.add(respProf.getUser());
        }

        return ResponseEntity.ok(new ResponseDto<>(respUserDtoList));
    }


    @GetMapping("/current")
    public ResponseEntity<ResponseDto<RespProfileDto>> get() {
        ResponseDto<RespProfileDto> respDto = service.getCurrentCashier();
        return ResponseEntity.ok(respDto);
    }


    @PostMapping("/current/{id}")
    public ResponseEntity<ResponseDto<RespProfileDto>> update(@PathVariable(value = "id") Long cashierId) {


        ResponseDto<RespProfileDto> respDto = service.updateCurrentCashierInSumOrd(cashierId);

        return ResponseEntity.ok(respDto);
    }

}
