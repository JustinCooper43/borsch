package com.nayax.borsch.controller;


import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.order.request.ReqPayConfirmDto;
import com.nayax.borsch.model.dto.order.response.RespPaymentInfoDto;
import com.nayax.borsch.service.impl.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<RespPaymentInfoDto>> get(@PathVariable(value = "id") Long id) {
        ResponseDto<RespPaymentInfoDto> responseDto = paymentService.getPaymentStatusByUserId(id);
        return ResponseEntity.ok(responseDto);
    }

/*
    @PostMapping("/complete")
    public ResponseEntity<ResponseDto<Boolean>> complete(@RequestBody ReqPayCompletedDto reqDto) {
        ResponseDto<Boolean> result = new ResponseDto<>(Boolean.TRUE);
        return ResponseEntity.ok(result);
    }
*/

    @PostMapping("/confirm")
    public ResponseEntity<ResponseDto<Boolean>> confirm(@RequestBody ReqPayConfirmDto reqDto) {
        ResponseDto<Boolean> result = paymentService.confirmPayment(reqDto);
        return ResponseEntity.ok(result);
    }
}
