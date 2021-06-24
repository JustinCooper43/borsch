package com.nayax.borsch.controller.testController;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.response.RespPriceItemDto;
import com.nayax.borsch.model.dto.order.response.RespOrderSumInfoDto;
import com.nayax.borsch.repository.impl.OrderItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/totalcost")
public class TestControllerTotalCostInfo {

    @Autowired
    OrderItemRepo orderItemRepo;

    @GetMapping("/")
    public ResponseEntity<ResponseDto<RespOrderSumInfoDto>> getOrderInfo(@RequestParam(required = false) LocalDateTime dateTime) {
        RespOrderSumInfoDto info = new RespOrderSumInfoDto();
        info.setPayAmount(new BigDecimal("5000"));
        info.setPayCompleted(new BigDecimal("4000"));
        info.setPayConfirmed(new BigDecimal("1000"));
        info.setPayUnconfirmed(new BigDecimal("3000"));

        LocalDateTime time = LocalDateTime.of(2020,10,10, 10,10);

        orderItemRepo.getOrderInfo(time);

        ResponseDto<RespOrderSumInfoDto> responseDto = new ResponseDto<>(info);

        return ResponseEntity.ok(responseDto);
    }

}
