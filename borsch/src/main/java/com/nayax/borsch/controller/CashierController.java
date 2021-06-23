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
        ResponseDto<RespProfileDto> respDto = new ResponseDto<>();
        return ResponseEntity.ok(respDto);
    }


    private List<RespUserDto> generateMockList() {
        List<RespUserDto> listResult = new ArrayList<>();
        int quantityPages = 8;
        for (int i = 0; i < quantityPages; i++) {
            listResult.add(generatedMock().getUser());
        }
        return listResult;
    }


    private RespProfileDto generatedMock() {
        RespProfileDto respDto = new RespProfileDto();

        RespUserDto user = new RespUserDto();
        RespCashierDto cashier = new RespCashierDto();
        RoleDto roleDto = new RoleDto();
        CreditCardDto creditCardDto = new CreditCardDto();

        roleDto.setId(4L);
        roleDto.setName("Cashier");

        user.setId(5L);
        user.setFirstName("RespProfileDto name");
        user.setLastName("RespProfileDto lastName");
        user.seteMail("respProfileDto@gmail.com");
        user.setRole(roleDto);
        user.setPhone("9379992");


        creditCardDto.setQr("data:image/png;base64,XDg5UE5HCgpcMDBcMDBcMDAKSUhEUlwwMFwwMFwwMFxDOFwwMFwwMFwwMFxDOFwwMFwwMFwwMFw5N1w5NjxcRERcMDBcMDBcMDBQTFRFXEZGXEZGXEZGXDAwXDAwXDAwVVxDMlxEM35cMDBcMDBcMDAJcEhZc1wwMFwwMFxDNFwwMFwwMFxDNFw5NStcMDBcMDBcMDBcQTVJREFUWFw4NVxFRFw5NVFcODAwQ1xCOVxGRlxBNWsyClxDQ1xFOHxcOEIkXDkyXEMxXEUzXEE3XEFCfDxcOTRcOTFcODdcQTlcQjhcQTRcQjIyXEREekhcQjJcQkFcRUMKT3x955CZXDgwXDkzXENBXEM5X3ZcOTRFXEVDClxBRFxGNlxFMSEsXEQyXEYxXEVDIFxDOSo1ZjFcOThJIlvaplxEMGRcRjNcQURuXyBcOTRcRjhhSTNAJmFcRkZcQkZcQTFJClxFNFxGN2IkLFxEMk5cREXsvok4XEUyXEVDXEJGUlw5OSBcOThcRjhcOUNKN1xBOXxcQkVcQzE4XDgxXEQ0TlxCNlxGN1FcQzlcQzZzWylcOTJYWylWCkNcQzlcOEVcQTJcQjA5KjNcQzdCUlwwMFwwMFwwMFwwMElFTkRcQUVCYFw4Mg");
        creditCardDto.setBankName("PrivatBank");
        creditCardDto.setNotes("Note about bank");
        creditCardDto.setCreditCard("1234 5678 9011 1234");

        cashier.setCash(true);
        cashier.setCard(creditCardDto);

        respDto.setUser(user);
        respDto.setPayments(cashier);

        return respDto;
    }

    private ProfileEntity createEntity() {
        ProfileEntity entity = new ProfileEntity();
        UserEntity userEntity = new UserEntity();
        CashierEntity cashierEntity = new CashierEntity();

        userEntity.seteMail("sdfdaf");
        userEntity.setActive("a");
        userEntity.setId(123232L);
        userEntity.setFirstName("ffffff");
        userEntity.setLastName("lllllllll");

        cashierEntity.setCashierId(12L);
        cashierEntity.setCardBank("qwef");
        cashierEntity.setCardNote("sdfwef");
        cashierEntity.setCardNumber("421234234");
        cashierEntity.setCardQrCode("qrqrqrqrq");
        cashierEntity.setCashPaymentAllowed(true);

        entity.setCashierEntity(cashierEntity);
        entity.setUserEntity(userEntity);
        return entity;
    }
}
