package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.order.response.RespOrderSumDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/timer")
public class OrderTimerController {
    @PostMapping("/start")
    public ResponseEntity<ResponseDto<RespOrderSumDto>> startOrder(Long userId, LocalDateTime finish){
        return null;
    }

    @PostMapping("/stop")
    public ResponseEntity<ResponseDto<Boolean>> stop(Long sumOrderId) {
        return ResponseEntity.ok(new ResponseDto<>(false));
    }
}
