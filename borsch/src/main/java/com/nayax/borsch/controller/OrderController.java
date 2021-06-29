package com.nayax.borsch.controller;

import com.nayax.borsch.mapper.OrderItemMapper;
import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.model.dto.order.request.ReqOrderItemAddDto;
import com.nayax.borsch.model.dto.order.response.RespOrderDeliveryDto;
import com.nayax.borsch.model.dto.order.response.RespOrderItemDto;
import com.nayax.borsch.model.dto.order.response.RespOrderSumDto;
import com.nayax.borsch.model.dto.order.response.RespOrderSumInfoDto;
import com.nayax.borsch.model.entity.order.OrderEntity;
import com.nayax.borsch.service.impl.OrderItemService;
import com.nayax.borsch.service.impl.OrderSummaryInfoService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderSummaryInfoService summaryInfoService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderSummaryInfoService orderSummaryInfoService;

    private RespOrderItemDto getRespOrderMock() {
        RespOrderItemDto orderItem = new RespOrderItemDto();

        RespSimplePriceItemDto shawa = new RespSimplePriceItemDto();
        shawa.setId(2l);
        shawa.setName("Щаурма с курицей");
        shawa.setPrice(new BigDecimal("90.00"));
        orderItem.setDish(shawa);

        RespSimplePriceItemDto addition = new RespSimplePriceItemDto();
        addition.setId(4l);
        addition.setName("Картошка");
        addition.setPrice(new BigDecimal("10.00"));
        List<RespSimplePriceItemDto> respSimpleItemDtos = List.of(addition, addition, addition, addition, addition, addition, addition);
        orderItem.setAdditions(respSimpleItemDtos);

        RespSimplePriceItemDto drink = new RespSimplePriceItemDto();
        addition.setId(4l);
        addition.setName("Coca-cola");
        addition.setPrice(new BigDecimal("23.50"));
        orderItem.setDrink(drink);

        RespSimpleItemDto remark = new RespSimpleItemDto();
        remark.setId(1l);
        remark.setName("побольше соуса");
        orderItem.setRemark(remark);
        orderItem.setCut(false);
        orderItem.setQuantity(2);

        return orderItem;
    }

    @PostMapping//Vlad
    public ResponseEntity<ResponseDto<RespOrderItemDto>> addOrder(@RequestBody ReqOrderItemAddDto dto) {
        OrderEntity order = Mappers.getMapper(OrderItemMapper.class).toAddEntity(dto);
        ResponseDto<RespOrderItemDto> result = orderItemService.addOrder(order);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<RespOrderItemDto>>> getPagedOrders(Long userId, @RequestParam(required = false) LocalDateTime dateTime) {
        return ResponseEntity.ok(orderItemService.getListOrder(userId,dateTime));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<ResponseDto<PageDto<RespOrderItemDto>>> getPagedHistory(
            @PathVariable(value = "userId") Long userId, @RequestParam int page, @RequestParam int pageSize) {
        RespOrderItemDto orderItem = getRespOrderMock();
        List<RespOrderItemDto> itemList = List.of(orderItem, orderItem, orderItem, orderItem, orderItem, orderItem, orderItem);
        PageDto<RespOrderItemDto> pageDto = PageDto.getPagedList(page, pageSize, itemList);
        ResponseDto<PageDto<RespOrderItemDto>> responseDto = new ResponseDto<>(pageDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/summary")///Vlad
    public ResponseEntity<ResponseDto<PageDto<RespOrderSumDto>>> getOrderSummary(
            @RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam(required = false) String date) {
        LocalDate localDate = LocalDate.parse(date);
        //ResponseDto<PageDto<RespOrderSumDto>> b =  summaryInfoService.getSummaryOrder(localDate,page,pageSize);
        return ResponseEntity.ok().body(summaryInfoService.getSummaryOrder(localDate,page,pageSize));
    }

    @GetMapping("/summary/info")
    public ResponseEntity<ResponseDto<RespOrderSumInfoDto>> getOrderInfo(@RequestParam(required = false) LocalDateTime dateTime) {
        RespOrderSumInfoDto info = new RespOrderSumInfoDto();
        info.setPayAmount(new BigDecimal("5000"));
        info.setPayCompleted(new BigDecimal("4000"));
        info.setPayConfirmed(new BigDecimal("1000"));
        info.setPayUnconfirmed(new BigDecimal("3000"));

        orderSummaryInfoService.getOrderSumInfo(dateTime);

        ResponseDto<RespOrderSumInfoDto> responseDto = new ResponseDto<>(info);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/delivery")
    public ResponseEntity<ResponseDto<List<RespOrderDeliveryDto>>> getDelivery(@RequestParam(required = false) LocalDateTime dateTime) {
        RespOrderDeliveryDto deliveryInfo = new RespOrderDeliveryDto();
        deliveryInfo.setOrder(getRespOrderMock());
        deliveryInfo.setOrderDate(dateTime);
        deliveryInfo.setQuantity(3);
        List<RespOrderDeliveryDto> pages = List.of(deliveryInfo, deliveryInfo, deliveryInfo, deliveryInfo,
                deliveryInfo, deliveryInfo, deliveryInfo, deliveryInfo, deliveryInfo);
        ResponseDto<List<RespOrderDeliveryDto>> responseDto = new ResponseDto<>(pages);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<RespOrderItemDto>> deleteOrder(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(orderItemService.deleteOrder(id));
    }
}
