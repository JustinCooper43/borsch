package com.nayax.borsch.controller;


import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.order.request.ReqPayCompletedDto;
import com.nayax.borsch.model.dto.order.request.ReqPayConfirmDto;
import com.nayax.borsch.model.dto.order.response.RespPaymentInfoDto;
import com.nayax.borsch.model.dto.user.response.RespCashierDto;
import com.nayax.borsch.model.dto.user.response.RespUserDto;
import com.nayax.borsch.model.dto.user.response.nested.CreditCardDto;
import com.nayax.borsch.model.dto.user.response.nested.RoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {


    @GetMapping("/")
    public ResponseEntity<ResponseDto<RespPaymentInfoDto>> get() {
        ResponseDto<RespPaymentInfoDto> responseDto = new ResponseDto<>(generatorPayDto());
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/complete/")
    public ResponseEntity<ResponseDto<Boolean>> complete(@RequestBody ReqPayCompletedDto reqDto) {
        ResponseDto<Boolean> result = new ResponseDto<>(Boolean.TRUE);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/confirm")
    public ResponseEntity<ResponseDto<Boolean>> confirm(@RequestBody ReqPayConfirmDto reqDto) {
        ResponseDto<Boolean> result = new ResponseDto<>(Boolean.TRUE);
        return ResponseEntity.ok(result);
    }


    private RespPaymentInfoDto generatorPayDto() {
        RespPaymentInfoDto result = new RespPaymentInfoDto();
        RespUserDto user = new RespUserDto();
        RespCashierDto cashier = new RespCashierDto();
        RoleDto roleDto = new RoleDto();
        CreditCardDto creditCardDto = new CreditCardDto();

        roleDto.setId(2L);
        roleDto.setName("Cashier");

        user.setId(2L);
        user.setFirstName("RespPaymentInfo name");
        user.setLastName("RespPaymentInfo lastName");
        user.seteMail("email@gmail.com");
        user.setRole(roleDto);
        user.setPhoneNumber("9379992");


        creditCardDto.setQrCode("data:image/png;base64,XDg5UE5HCgpcMDBcMDBcMDAKSUhEUlwwMFwwMFwwMFxDOFwwMFwwMFwwMFxDOFwwMFwwMFwwMFw5N1w5NjxcRERcMDBcMDBcMDBQTFRFXEZGXEZGXEZGXDAwXDAwXDAwVVxDMlxEM35cMDBcMDBcMDAJcEhZc1wwMFwwMFxDNFwwMFwwMFxDNFw5NStcMDBcMDBcMDBcQTVJREFUWFw4NVxFRFw5NVFcODAwQ1xCOVxGRlxBNWsyClxDQ1xFOHxcOEIkXDkyXEMxXEUzXEE3XEFCfDxcOTRcOTFcODdcQTlcQjhcQTRcQjIyXEREekhcQjJcQkFcRUMKT3x955CZXDgwXDkzXENBXEM5X3ZcOTRFXEVDClxBRFxGNlxFMSEsXEQyXEYxXEVDIFxDOSo1ZjFcOThJIlvaplxEMGRcRjNcQURuXyBcOTRcRjhhSTNAJmFcRkZcQkZcQTFJClxFNFxGN2IkLFxEMk5cREXsvok4XEUyXEVDXEJGUlw5OSBcOThcRjhcOUNKN1xBOXxcQkVcQzE4XDgxXEQ0TlxCNlxGN1FcQzlcQzZzWylcOTJYWylWCkNcQzlcOEVcQTJcQjA5KjNcQzdCUlwwMFwwMFwwMFwwMElFTkRcQUVCYFw4Mg");
        creditCardDto.setBank("alfaBank");
        creditCardDto.setNote("Note about bank");
        creditCardDto.setNumber("1234 5678 9011 1234");

        cashier.setCashierId(3L);
        cashier.setCashPaymentAllowed(true);
        cashier.setCreditCard(creditCardDto);

        result.setCashier(user);
        result.setPaymentMethod(cashier);
        result.setCompleted(true);
        result.setConfirmed(true);
        return result;
    }
}
