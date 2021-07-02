package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.order.request.ReqOrderItemAddDto;
import com.nayax.borsch.model.dto.order.response.RespOrderDeliveryDto;
import com.nayax.borsch.model.dto.order.response.RespOrderItemDto;
import com.nayax.borsch.model.dto.order.response.RespOrderSumDto;
import com.nayax.borsch.model.dto.order.response.RespOrderSumInfoDto;
import com.nayax.borsch.service.impl.DeliveryService;
import com.nayax.borsch.service.impl.OrderItemService;
import com.nayax.borsch.service.impl.OrderSummaryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    DeliveryService deliveryService;
    @Autowired
    OrderSummaryInfoService summaryInfoService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderSummaryInfoService orderSummaryInfoService;

    @PostMapping
    public ResponseEntity<ResponseDto<RespOrderItemDto>> addOrder(@RequestBody ReqOrderItemAddDto dto) {
        ResponseDto<RespOrderItemDto> result = orderItemService.addOrder(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<RespOrderItemDto>>> getListOrders(Long userId, @RequestParam(required = false) String date) {
        return ResponseEntity.ok(orderItemService.getListOrder(userId, date));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<ResponseDto<PageDto<RespOrderItemDto>>> getPagedHistory(
            @PathVariable(value = "userId") Long userId, @RequestParam int page, @RequestParam int pageSize) {

        return ResponseEntity.ok().body(orderItemService.getPagedHistory(userId, page, pageSize));
    }

    @GetMapping("/summary")///Vlad
    public ResponseEntity<ResponseDto<PageDto<RespOrderSumDto>>> getOrderSummary(
            @RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam(required = false) String date) {
        LocalDate localDate = LocalDate.parse(date);
        //ResponseDto<PageDto<RespOrderSumDto>> b =  summaryInfoService.getSummaryOrder(localDate,page,pageSize);
        return ResponseEntity.ok().body(summaryInfoService.getSummaryOrder(localDate, page, pageSize));
    }

    @GetMapping("/summary/info")
    public ResponseEntity<ResponseDto<RespOrderSumInfoDto>> getOrderInfo(@RequestParam(required = false) String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(orderSummaryInfoService.getOrderSumInfo(localDate));
    }

    @GetMapping("/delivery")
    public ResponseEntity<ResponseDto<PageDto<RespOrderDeliveryDto>>> getDelivery(
            @RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam(required = false) String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        ResponseDto<PageDto<RespOrderDeliveryDto>> response = deliveryService.getPagedDeliveryInfo(page, pageSize, date);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<RespOrderItemDto>> deleteOrder(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(orderItemService.deleteOrder(id));
    }
}
