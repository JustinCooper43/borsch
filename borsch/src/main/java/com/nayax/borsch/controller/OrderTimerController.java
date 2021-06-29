package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")
public class OrderTimerController {
    @PostMapping("/start")
    public ResponseEntity<ResponseDto<Boolean>> startOrder(Long userId, @RequestParam(required = false) LocalDateTime finish) {
        return null;
    }

    @PostMapping("/stop")
    public ResponseEntity<ResponseDto<Boolean>> stopOrder(@RequestParam Long sumOrderId) {
        return ResponseEntity.ok(new ResponseDto<>(false));
    }

    @GetMapping("/status")
    public ResponseEntity<ResponseDto<RespOrderStatusDto>> getOrderStatus (){
        return null;
    }
}
