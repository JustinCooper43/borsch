package com.nayax.borsch.controller;


import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.order.response.RespPaymentInfoDto;
import com.nayax.borsch.model.dto.user.response.RespCashierDto;
import com.nayax.borsch.model.dto.user.response.RespProfileDto;
import com.nayax.borsch.model.dto.user.response.RespUserDto;
import com.nayax.borsch.model.dto.user.response.nested.CreditCardDto;
import com.nayax.borsch.model.dto.user.response.nested.RoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cashier")
public class CashierController {


    @PostMapping
    public ResponseEntity<ResponseDto<RespProfileDto>> getById(@RequestParam Long id) {
        ResponseDto<RespProfileDto> respDto = new ResponseDto<>(generatedMock());
        return ResponseEntity.ok(respDto);
    }

    @GetMapping("/dropdown")
    public ResponseEntity<ResponseDto<List<RespUserDto>>> getAllUsers() {
        ResponseDto<List<RespUserDto>> respListDto = new ResponseDto<>(generateMockList());
        return ResponseEntity.ok(respListDto);
    }

    @PostMapping("/current")
    public ResponseEntity<ResponseDto<RespProfileDto>> get() {
        ResponseDto<RespProfileDto> respDto = new ResponseDto<>(generatedMock());
        return ResponseEntity.ok(respDto);
    }


    private  List<RespUserDto> generateMockList() {
        List<RespUserDto> listResult = new ArrayList<>();
        int quantityPages = 8;
        for(int i = 0; i < quantityPages; i++){
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
        user.setPhoneNumber("9379992");


        creditCardDto.setQrCode("data:image/png;base64,XDg5UE5HCgpcMDBcMDBcMDAKSUhEUlwwMFwwMFwwMFxDOFwwMFwwMFwwMFxDOFwwMFwwMFwwMFw5N1w5NjxcRERcMDBcMDBcMDBQTFRFXEZGXEZGXEZGXDAwXDAwXDAwVVxDMlxEM35cMDBcMDBcMDAJcEhZc1wwMFwwMFxDNFwwMFwwMFxDNFw5NStcMDBcMDBcMDBcQTVJREFUWFw4NVxFRFw5NVFcODAwQ1xCOVxGRlxBNWsyClxDQ1xFOHxcOEIkXDkyXEMxXEUzXEE3XEFCfDxcOTRcOTFcODdcQTlcQjhcQTRcQjIyXEREekhcQjJcQkFcRUMKT3x955CZXDgwXDkzXENBXEM5X3ZcOTRFXEVDClxBRFxGNlxFMSEsXEQyXEYxXEVDIFxDOSo1ZjFcOThJIlvaplxEMGRcRjNcQURuXyBcOTRcRjhhSTNAJmFcRkZcQkZcQTFJClxFNFxGN2IkLFxEMk5cREXsvok4XEUyXEVDXEJGUlw5OSBcOThcRjhcOUNKN1xBOXxcQkVcQzE4XDgxXEQ0TlxCNlxGN1FcQzlcQzZzWylcOTJYWylWCkNcQzlcOEVcQTJcQjA5KjNcQzdCUlwwMFwwMFwwMFwwMElFTkRcQUVCYFw4Mg");
        creditCardDto.setBank("PrivatBank");
        creditCardDto.setNote("Note about bank");
        creditCardDto.setNumber("1234 5678 9011 1234");

        cashier.setCashierId(3L);
        cashier.setCashPaymentAllowed(true);
        cashier.setCreditCard(creditCardDto);

        respDto.setUser(user);
        respDto.setPayments(cashier);

        return respDto;
    }
}
