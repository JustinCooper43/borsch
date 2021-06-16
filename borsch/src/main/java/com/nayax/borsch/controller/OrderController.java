package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.order.request.ReqOrderItemAddDto;
import com.nayax.borsch.model.dto.order.response.RespOrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private RespOrderDto getRespOrderMock() {
        RespOrderDto orderItem = new RespOrderDto();
        orderItem.setItemCount(2);
        orderItem.setExtraItemId(3L);
        orderItem.setAdditionIdList(List.of(2L, 4L, 6L));
        orderItem.setCutInHalf(false);
        orderItem.setRemarkId(1L);
        orderItem.setTypeId(4L);
        return orderItem;
    }

    @PostMapping("/")
    public ResponseEntity<ResponseDto<RespOrderDto>> addOrder(@RequestBody ReqOrderItemAddDto dto) {
        RespOrderDto orderItem = getRespOrderMock();
        ResponseDto<RespOrderDto> responseDto = new ResponseDto<>(orderItem);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDto<List<RespOrderDto>>> getPagedOrders(
            @RequestParam int page, @RequestParam int pageSize, @RequestParam Long userId, @RequestParam LocalDateTime dateTime) {
        RespOrderDto orderItem = getRespOrderMock();
        List<RespOrderDto> itemList = List.of(orderItem, orderItem, orderItem, orderItem, orderItem, orderItem, orderItem);
        ResponseDto<List<RespOrderDto>> responseDto = new ResponseDto<>(itemList);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/history")
    public ResponseEntity<ResponseDto<List<RespOrderDto>>> getPagedHistory(
            @RequestParam Long userId, @RequestParam int page, @RequestParam int pageSize){
        RespOrderDto orderItem = getRespOrderMock();
        List<RespOrderDto> itemList = List.of(orderItem, orderItem, orderItem, orderItem, orderItem, orderItem, orderItem);
        ResponseDto<List<RespOrderDto>> responseDto = new ResponseDto<>(itemList);
        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/")
    public boolean deleteOrder(Long orderItemId){
        return true;
    }
}
