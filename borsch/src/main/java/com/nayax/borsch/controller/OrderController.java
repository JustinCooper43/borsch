package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.order.request.ReqOrderItemAddDto;
import com.nayax.borsch.model.dto.order.response.RespOrderDeliveryDto;
import com.nayax.borsch.model.dto.order.response.RespOrderDto;
import com.nayax.borsch.model.dto.order.response.RespOrderSumDto;
import com.nayax.borsch.model.dto.order.response.RespOrderSumInfoDto;
import com.nayax.borsch.model.dto.user.response.RespUserDto;
import com.nayax.borsch.model.dto.user.response.nested.RoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    private RespUserDto getUserMock() {
        RespUserDto user = new RespUserDto();
        user.setId(14L);
        user.setFirstName("Fname");
        user.setLastName("Lname");
        user.seteMail("adress@server.com");
        RoleDto role = new RoleDto();
        role.setId(2L);
        role.setName("Cashier");
        user.setRole(role);
        user.setPhone("+380123456789");
        return user;
    }


    @PostMapping
    public ResponseEntity<ResponseDto<RespOrderDto>> addOrder(@RequestBody ReqOrderItemAddDto dto) {
        RespOrderDto orderItem = getRespOrderMock();
        ResponseDto<RespOrderDto> responseDto = new ResponseDto<>(orderItem);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<PageDto<RespOrderDto>>> getPagedOrders(
            @RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam Long userId, @RequestParam(required = false) LocalDateTime dateTime) {
        RespOrderDto orderItem = getRespOrderMock();
        List<RespOrderDto> itemList = List.of(orderItem, orderItem, orderItem, orderItem, orderItem, orderItem, orderItem);
        PageDto<RespOrderDto> pageDto = new PageDto<>(itemList);
        pageDto.setTotalElements(10 * pageSize);
        pageDto.setTotalPages(10);
        pageDto.setElementsPerPage(pageSize);
        pageDto.setCurrentPageNumber(page);
        ResponseDto<PageDto<RespOrderDto>> responseDto = new ResponseDto<>(pageDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<ResponseDto<PageDto<RespOrderDto>>> getPagedHistory(
            @PathVariable(value="userId") Long userId, @RequestParam int page, @RequestParam int pageSize) {
        RespOrderDto orderItem = getRespOrderMock();
        List<RespOrderDto> itemList = List.of(orderItem, orderItem, orderItem, orderItem, orderItem, orderItem, orderItem);
        PageDto<RespOrderDto> pageDto = new PageDto<>(itemList);
        pageDto.setTotalElements(10 * pageSize);
        pageDto.setTotalPages(10);
        pageDto.setElementsPerPage(pageSize);
        pageDto.setCurrentPageNumber(page);
        ResponseDto<PageDto<RespOrderDto>> responseDto = new ResponseDto<>(pageDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/summary")
    public ResponseEntity<ResponseDto<RespOrderSumDto>> getOrderSummary(@RequestParam(required = false) LocalDateTime dateTime) {
        RespOrderSumDto orderSumDto = new RespOrderSumDto();
        RespOrderDto orderItem = getRespOrderMock();
        List<RespOrderDto> itemList = List.of(orderItem, orderItem, orderItem, orderItem, orderItem, orderItem, orderItem);
        orderSumDto.setOrderDate(LocalDateTime.now().minusMinutes(10));
        orderSumDto.setOrders(itemList);
        orderSumDto.setUser(getUserMock());
        orderSumDto.setAmount(new BigDecimal("40.3"));
        orderSumDto.setPaidAmount(new BigDecimal("40.2"));
        orderSumDto.setPaymentType(2L);
        ResponseDto<RespOrderSumDto> responseDto = new ResponseDto<>(orderSumDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/summary/info")
    public ResponseEntity<ResponseDto<RespOrderSumInfoDto>> getOrderInfo(@RequestParam(required = false) LocalDateTime dateTime) {
        RespOrderSumInfoDto info = new RespOrderSumInfoDto();
        info.setPayAmount(new BigDecimal("5000"));
        info.setPayCompleted(new BigDecimal("4000"));
        info.setPayConfirmed(new BigDecimal("1000"));
        info.setPayUnconfirmed(new BigDecimal("3000"));
        ResponseDto<RespOrderSumInfoDto> responseDto = new ResponseDto<>(info);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/delivery")
    public ResponseEntity<ResponseDto<List<RespOrderDeliveryDto>>> getDelivery(@RequestParam(required = false) LocalDateTime dateTime) {
        RespOrderDeliveryDto deliveryInfo = new RespOrderDeliveryDto();
        deliveryInfo.setOrder(getRespOrderMock());
        deliveryInfo.setOrderDate(dateTime);
        deliveryInfo.setQuantity(3L);
        List<RespOrderDeliveryDto> pages = List.of(deliveryInfo, deliveryInfo, deliveryInfo, deliveryInfo,
                deliveryInfo, deliveryInfo, deliveryInfo, deliveryInfo, deliveryInfo);
        ResponseDto<List<RespOrderDeliveryDto>> responseDto = new ResponseDto<>(pages);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Boolean>> deleteOrder(@PathVariable(value="id") Long id) {
        ResponseDto<Boolean> result = new ResponseDto<>(Boolean.TRUE);
        return ResponseEntity.ok(result);
    }
}
