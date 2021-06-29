package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.order.request.ReqOrderStartDto;
import com.nayax.borsch.model.dto.order.response.RespOrderStatusDto;
import com.nayax.borsch.service.impl.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderTimerController {
    @Autowired
    TimerService timerService;

    @PostMapping("/start")
    public ResponseEntity<ResponseDto<Boolean>> startOrder(@RequestParam ReqOrderStartDto dto) {
        return ResponseEntity.ok(timerService.startOrder(dto));
    }

    @PostMapping("/stop")
    public ResponseEntity<ResponseDto<Boolean>> stopOrder() {
        return ResponseEntity.ok(timerService.stopOrder());
    }

    @GetMapping("/status")
    public ResponseEntity<ResponseDto<RespOrderStatusDto>> getOrderStatus() {
        return ResponseEntity.ok(timerService.getOrderStatus());
    }
}
